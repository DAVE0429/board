package com.board.api.dto.board;

import com.board.api.dto.category.CategoryResponseDto;
import com.board.api.entity.Board;
import com.board.api.entity.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {
    private Long id;
    private CategoryResponseDto category;
    private String title;
    private String content;
    private String author;
    private Long likeCount;
    private boolean liked;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss")
    private LocalDateTime createdDate;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss")
    private LocalDateTime modifiedDate;

    @Builder
    public BoardResponseDto(Long id, CategoryResponseDto category, String title, String content, String author, Long likeCount, boolean liked, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.content = content;
        this.author = author;
        this.likeCount = likeCount;
        this.liked = liked;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;


    }

    public static BoardResponseDto from(Board board) {
        CategoryResponseDto categoryResponseDto = CategoryResponseDto.from(board.getCategory());
        return BoardResponseDto.builder()
                .id(board.getId())
                .category(categoryResponseDto)
                .title(board.getTitle())
                .content(board.getContent())
                .author(board.getMember().getName())
                .likeCount(board.getLikeCount())
                .createdDate(board.getCreatedDate())
                .modifiedDate(board.getModifiedDate())
                .build();
    }
}
