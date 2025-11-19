package com.board.api.service;

import com.board.api.dto.category.CategoryResponseDto;
import com.board.api.dto.category.CreateCategoryRequestDto;
import com.board.api.dto.category.UpdateCategoryRequestDto;
import com.board.api.entity.Board;
import com.board.api.entity.Category;
import com.board.api.repository.BoardRepository;
import com.board.api.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final BoardRepository boardRepository;

    // 카테고리 등록
    @Transactional
    public CategoryResponseDto createCategory(CreateCategoryRequestDto categoryRequestDto){
        Category category = categoryRequestDto.toEntity();

        categoryRepository.save(category);

        return CategoryResponseDto.from(category);
    }

    // 카테고리 수정
    @Transactional
    public CategoryResponseDto updateCategory(UpdateCategoryRequestDto updateCategoryRequestDto, Long id){
        Category category = categoryRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("해당 카테고리를 찾을 수 없습니다."));

        category.update(updateCategoryRequestDto.getName());

        return CategoryResponseDto.from(category);
    }

    // 카테고리 삭제
    @Transactional
    public Long deleteCategory(Long id){
        Category category = categoryRepository.findById(id).orElseThrow(()-> new RuntimeException("카테고리를 찾을 수 없습니다."));

        boolean hasBoards = boardRepository.existsByCategory(category);
        if(hasBoards){
            throw new RuntimeException("해당 카테고리에 게시글이 있어 삭제할 수 없습니다.");
        }

        categoryRepository.deleteById(id);
        return id;
    }

    // 카테고리 리스트 조회
    public List<CategoryResponseDto> findAllCategories(){
        try{
            List<Category> categories = categoryRepository.findAll();
            List<CategoryResponseDto> categoryResponseDtos = categories.stream()
                    .map(CategoryResponseDto::from)
                    .toList();
            if(categories == null){
                throw new IllegalStateException("등록된 카테고리가 없습니다.");
            }
            return categoryResponseDtos;
        } catch (DataAccessException e){
            throw new RuntimeException("데이터 베이스 접근 중 오류가 발생했습니다.",e);
        } catch (Exception e){
            throw new RuntimeException("게시글 조회 중 오류가 발생했습니다.",e);
        }
    }

    // 카테고리 조회
    public CategoryResponseDto findById(Long id){
        Category category = categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 카테고리가 존재하지 않습니다. id = " + id ));

        return CategoryResponseDto.from(category);
    }




}

