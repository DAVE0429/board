package com.board.api.dto.board;

import com.board.api.dto.category.CategoryResponseDto;
import com.board.api.entity.Board;
import com.board.api.entity.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardResponseDto {
    private Long id;
    private CategoryResponseDto category;
    private String title;
    private String content;
    private String author;
    private Long likeCount;
    private String createdDate;
    private String modifiedDate;

    @Builder
    public BoardResponseDto(Long id, CategoryResponseDto category, String title, String content, String author, String createdDate, String modifiedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.category = category;

    }

    public static BoardResponseDto from(Board board) {
        CategoryResponseDto categoryResponseDto = CategoryResponseDto.from(board.getCategory());
        return BoardResponseDto.builder()
                .id(board.getId())
                .category(categoryResponseDto)
                .title(board.getTitle())
                .content(board.getContent())
                .author(board.getMember().getName())
                .createdDate(board.getCreatedDate())
                .modifiedDate(board.getModifiedDate())
                .build();
    }
}
