package com.board.api.controller;

import com.board.api.dto.category.CategoryResponseDto;
import com.board.api.dto.category.CreateCategoryRequestDto;
import com.board.api.dto.category.UpdateCategoryRequestDto;
import com.board.api.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="카테고리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/_api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "카테고리 - 카테고리 생성")
    @PostMapping("")
    public ResponseEntity create(@RequestBody CreateCategoryRequestDto categoryRequestDto){
        CategoryResponseDto categoryResponseDto = categoryService.createCategory(categoryRequestDto);
        return ResponseEntity.ok().body(categoryResponseDto);
    }

    @Operation(summary = "카테고리 - 전체 카테고리 조회")
    @GetMapping("")
    public ResponseEntity<List<CategoryResponseDto>> findAllCategories(){
        List<CategoryResponseDto> results = categoryService.findAllCategories();
        return ResponseEntity.ok(results);
    }

    @Operation(summary = "카테고리 - 카테고리 하나 조회")
    @GetMapping("{id}")
    public ResponseEntity findById(@PathVariable Long id){
        CategoryResponseDto result = categoryService.findById(id);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "카테고리 - 카테고리 수정")
    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable("id") Long id,@RequestBody UpdateCategoryRequestDto updateCategoryRequestDto){
        CategoryResponseDto categoryResponseDto = categoryService.updateCategory(updateCategoryRequestDto,id);
        return ResponseEntity.ok().body(categoryResponseDto);
    }

    @Operation(summary = "카테고리 - 카테고리 삭제")
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        Long categoryId = categoryService.deleteCategory(id);
        return ResponseEntity.ok().body(categoryId);
    }


}
