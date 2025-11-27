package com.board.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FileDto {

    @Schema(description = " 파일 이름 ", example = "fu.jpeg")
    private String fileName;

    @Schema(description = " s3 파일 주소 ", example = "http://~fu.jpeg")
    private String imageUrl;

    @Schema(description = " 원본 파일 주소 ", example = "fu.jpeg")
    private String originalName;

    public FileDto(String fileName, String imageUrl, String originalName){
        this.fileName = fileName;
        this.imageUrl = imageUrl;
        this.originalName = originalName;
    }
}
