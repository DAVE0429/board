package com.board.api.dto.board;

import com.board.api.entity.Category;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateBoardRequestDto {
    @NotEmpty(message="제목을 입력해주세요.")
    private String title;

    @NotEmpty(message="내용을 입력해주세요.")
    private String content;

    @NotEmpty(message="카테고리를 입력해주세요.")
    private Long categoryId;

    @Builder
    public UpdateBoardRequestDto(String title, String content, Long categoryId){
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
    }
}
