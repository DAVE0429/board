package com.board.api.service;

import com.board.api.entity.Category;
import com.board.api.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public Long create(Category category){
        categoryRepository.save(category);

        return category.getId();
    }

    public List<Category> findAll(){

        List<Category> categories = categoryRepository.findAll();

        return categories;
    }

    public Long remove(Long id){

        Category category = categoryRepository.findById(id);
        categoryRepository.delete(id);

        return id;
    }

    // 카테고리 만들기 보드 리스트 만들기


}

