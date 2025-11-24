package com.board.api.dto.board;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardListResponseDto {
    private Long id;
    private String title;
    private String author;
    private Long likeCount;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss")
    private LocalDateTime createdDate;

    @Builder
    public BoardListResponseDto(Long id, String title, String author, Long likeCount, LocalDateTime createdDate){
        this.id = id;
        this.title = title;
        this.author = author;
        this.likeCount = likeCount;
        this.createdDate = createdDate;
    }
}
