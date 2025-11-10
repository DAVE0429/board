package com.board.api.dto.board;

import com.board.api.entity.Board;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private String createdDate;
    private String modifiedDate;

    @Builder
    public BoardResponseDto(Long id, String title, String content, String author, String createdDate, String modifiedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;

    }

    public static BoardResponseDto from(Board board) {
        return BoardResponseDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .author(board.getMember().getName())
                .createdDate(board.getCreatedDate())
                .modifiedDate(board.getModifiedDate())
                .build();
    }
}
