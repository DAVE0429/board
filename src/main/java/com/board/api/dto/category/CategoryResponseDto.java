package com.board.api.dto.category;

import com.board.api.entity.Category;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CategoryResponseDto {

    private Long id;

    private String name;

    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss")
    private LocalDateTime createdDate;

    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss")
    private LocalDateTime modifiedDate;

    @Builder
    public CategoryResponseDto(Long id, String name, LocalDateTime createdDate, LocalDateTime modifiedDate){
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public static CategoryResponseDto from(Category category){
        return CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .createdDate(category.getCreatedDate())
                .modifiedDate(category.getModifiedDate()).build();
    }
}
