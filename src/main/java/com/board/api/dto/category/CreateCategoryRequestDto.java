package com.board.api.dto.category;

import com.board.api.entity.Category;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateCategoryRequestDto {

    @NotEmpty(message = "카테고리를 입력해 주세요.")
    String name;


    public CreateCategoryRequestDto(String name){
        this.name = name;
    }

    public Category toEntity(){
        return Category.
                builder().
                name(this.name).build();
    }

}
