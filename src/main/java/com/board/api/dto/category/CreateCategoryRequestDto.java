package com.board.api.dto.category;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CreateCategoryRequestDto {

    @NotEmpty(message = "카테고리를 입력해 주세요.")
    String name;

    @Builder
    public CreateCategoryRequestDto(String name){
        this.name = name;
    }

}
