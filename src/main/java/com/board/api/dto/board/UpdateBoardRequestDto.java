package com.board.api.dto.board;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateBoardRequestDto {
    @NotEmpty(message="제목을 입력해주세요.")
    private String title;

    @NotEmpty(message="내용을 입력해주세요.")
    private String content;

    @Builder
    public UpdateBoardRequestDto(String title, String content){
        this.title = title;
        this.content = content;
    }
}
