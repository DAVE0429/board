package com.board.api.dto.board;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardListResponseDto {
    private Long id;
    private String title;
    private String author;
    private String createdDate;

    @Builder
    public BoardListResponseDto(Long id, String title, String author, String createdDate){
        this.id = id;
        this.title = title;
        this.author = author;
        this.createdDate = createdDate;
    }
}
