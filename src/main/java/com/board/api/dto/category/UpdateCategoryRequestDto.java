package com.board.api.dto.category;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateCategoryRequestDto {
    @NotEmpty(message = "이름을 입력해주세요.")
    private String name;

    @Builder
    public UpdateCategoryRequestDto(String name){
        this.name = name;
    }

}
