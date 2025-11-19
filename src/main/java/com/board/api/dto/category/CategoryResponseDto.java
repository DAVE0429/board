package com.board.api.dto.category;

import com.board.api.entity.Category;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CategoryResponseDto {

    private Long id;

    private String name;

    private String createdDate;

    private String modifiedDate;

    @Builder
    public CategoryResponseDto(Long id, String name, String createdDate, String modifiedDate){
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
