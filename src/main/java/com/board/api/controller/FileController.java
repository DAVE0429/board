package com.board.api.controller;

import com.board.api.dto.file.DeleteFileResponseDto;
import com.board.api.dto.file.FileDto;
import com.board.api.entity.Member;
import com.board.api.enums.FileTargetType;
import com.board.api.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "파일")
@RestController
@RequiredArgsConstructor
@RequestMapping("/_api/v1/file")
public class FileController {

    private final FileService fileService;

    @Operation(summary = "파일 - 파일 업로드")
    @PostMapping(
            value = "/files",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<FileDto>> uploadFiles(@AuthenticationPrincipal Member member, @RequestParam FileTargetType targetType, @RequestParam Long targetId, @RequestPart List<MultipartFile> multipartFile){
        return ResponseEntity.ok(fileService.uploadFiles(member,targetType, targetId,multipartFile));
    }

    @Operation(summary = "파일 - 삭제")
    @DeleteMapping("files/{fileId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<DeleteFileResponseDto> deleteFile(@AuthenticationPrincipal Member member, @PathVariable Long fileId){
        return ResponseEntity.ok(fileService.deleteFile(member,fileId));
    }
}
