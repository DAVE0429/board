package com.board.api.service;

import com.board.api.dto.category.CategoryResponseDto;
import com.board.api.dto.category.CreateCategoryRequestDto;
import com.board.api.entity.Category;
import com.board.api.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CategoryServiceTest {
    @Autowired private CategoryService categoryService;

    @Test
    public void 카테고리생성() throws Exception{
        //given
        CreateCategoryRequestDto category = new CreateCategoryRequestDto("잡담");
        //when
        CategoryResponseDto categoryResponseDto = categoryService.createCategory(category);
        //then
        CategoryResponseDto categoryResponseDto1 = categoryService.findById(categoryResponseDto.getId());
        Assertions.assertEquals(categoryResponseDto1.getName(),categoryResponseDto.getName());
        Assertions.assertEquals(categoryResponseDto1.getName(), "잡담");

    }

    @Test
    public void 삭제() throws Exception{
        Long id = categoryService.deleteCategory(2L);
        System.out.println(id);
    }

}