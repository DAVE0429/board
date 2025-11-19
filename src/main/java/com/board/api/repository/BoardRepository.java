package com.board.api.repository;

import com.board.api.entity.Board;
import com.board.api.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findByTitleContaining(String keyword, Pageable pageable);
    boolean existsByCategory(Category category);
    Page<Board> findByCategory(Category category, Pageable pageable);
}
