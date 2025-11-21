package com.board.api.repository;

import com.board.api.entity.Board;
import com.board.api.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Long countByBoard(Board board);
}
