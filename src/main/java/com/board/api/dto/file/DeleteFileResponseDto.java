package com.board.api.dto.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class DeleteFileResponseDto {
    private Long id;
    private String storedFileName;
    private String fullPath;
    private String message;
}
