package com.board.api.repository;

import com.board.api.entity.Board;
import com.board.api.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    Page<Comment> findAllByBoard(Board board);
}
