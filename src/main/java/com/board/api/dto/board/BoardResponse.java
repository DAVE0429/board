package com.board.api.dto.board;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardResponse {
    private Long id;
    private String title;
    private String content;
    private String author;
    private String createdDate;
    private String modifiedDate;

    @Builder
    public BoardResponse(Long id, String title, String content, String author, String createdDate, String modifiedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;

    }
}
