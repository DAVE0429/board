package com.board.api.service;

import com.board.api.dto.file.DeleteFileResponseDto;
import com.board.api.dto.file.FileDto;
import com.board.api.entity.Files;
import com.board.api.entity.Member;
import com.board.api.enums.FileTargetType;
import com.board.api.repository.FileRepository;
import com.board.api.util.S3FileClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileService {

    private final S3FileClient s3FileClient;

    private final FileRepository fileRepository;

    // application. 에 설정된 S3 버킷 URL을 주입받음
    @Value("${cloud.aws.s3.bucket-url}")
    private String AWS_S3_BUCKET_URL;

    @Transactional
    private FileDto uploadFile(Member member, FileTargetType targetType, Long targetId, MultipartFile multipartFile){
        File initialTempFile = null; // 임시 파일 저장 변수
        try{
            // 파일이 없거나 비어 있으면 예외 처리
            if(multipartFile == null || multipartFile.isEmpty()){
                throw new RuntimeException("파일이 비었습니다.");
            }

            // MultipartFile -> 일반 File 변환 (임시 저장)
            initialTempFile = multipartFileToFile(multipartFile);

            String uploadDir = targetType.getDir();
            // 업로드된 파일의 원본 파일 이름
            String originalFilename = Optional.ofNullable(multipartFile.getOriginalFilename()).orElse("unknown");

            // 저장될 "실제 파일 이름"을 생성
            String storedName = UUID.randomUUID() + "_" + originalFilename;

            // Amazon S3에 업로드 후 반환되는 URL 저장
            String fileUrl = s3FileClient.uploadFile(uploadDir,storedName,initialTempFile);

            Files file = new Files(
                    member,
                    targetType,
                    targetId,
                    uploadDir,
                    originalFilename,
                    storedName,
                    fileUrl,
                    multipartFile.getSize()
            );

            fileRepository.save(file);

            // 파일 정보를 DTO로 반환
            return FileDto.from(file);

        } catch (IOException e){
            // 예외 발생 시 메세지 반환
            throw new RuntimeException("파일 업로드 중 에러");
        } finally {
            // 임시 파일 삭제 (로컬 저장소 정리)
            if(initialTempFile != null){
                initialTempFile.delete();
            }
        }
    }
    @Transactional
    public List<FileDto> uploadFiles(Member member, FileTargetType targetType, Long targetId, List<MultipartFile> multipartFiles){

        if(multipartFiles == null || multipartFiles.isEmpty()){
            throw new RuntimeException("업로드할 파일이 없습니다.");
        }

        List<FileDto> results = new ArrayList<>();

        multipartFiles.forEach(file -> {
            results.add(uploadFile(member, targetType, targetId, file));
        });

        return results;

    }

    // MultipartFile -> File 변환 (S3에 업로드 하기 위함)
    public File multipartFileToFile(MultipartFile multipartFile) throws IOException {
        // 임시 저장 경로 + UUID + 원본 파일명으로 저장
        File tempFile = new File(System.getProperty("java.io.tmpdir") + "/" + UUID.randomUUID() + "_" + multipartFile.getOriginalFilename());
        // MultipartFile을 실제 파일로 저장
        multipartFile.transferTo(tempFile);
        // 변환된 파일 반환;
        return tempFile;
    }

    // 파일 삭제
    @Transactional
    public DeleteFileResponseDto deleteFile(Member member, Long fileId) {

        Files file = fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("파일 없음"));

        if (!file.getUploader().getId().equals(member.getId())) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }

        s3FileClient.deleteFile(file.getUploadDir(), file.getStoredName());

        fileRepository.delete(file);

        return new DeleteFileResponseDto(
                file.getId(),
                file.getOriginalName(),
                file.getUrl(),
                "파일이 정상적으로 삭제되었습니다."
        );
    }
}
