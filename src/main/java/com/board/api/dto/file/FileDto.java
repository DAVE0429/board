package com.board.api.dto.file;

import com.board.api.entity.Files;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FileDto {

    @Schema(description = "파일 ID", example = "1")
    private Long fileId;

    @Schema(description = "원본 파일명", example = "profile.png")
    private String originalName;

    @Schema(description = "파일 접근 URL", example = "https://bucket.s3.ap-northeast-2.amazonaws.com/upload/uuid.png")
    private String url;

    @Schema(description = "파일 크기(bytes)", example = "204800")
    private Long size;

    public FileDto(Long fileId, String originalName, String url, Long size) {
        this.fileId = fileId;
        this.originalName = originalName;
        this.url = url;
        this.size = size;
    }

    public static FileDto from(Files file) {
        return new FileDto(
                file.getId(),
                file.getOriginalName(),
                file.getUrl(),
                file.getSize()
        );
    }
}
