package com.board.api.repository;

import com.board.api.entity.Board;
import com.board.api.entity.Comment;
import com.board.api.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    Page<Comment> findAllByBoardId(Long boardId, Pageable pageable);

    Page<Comment> findByMemberAndDeletedFalse(Member member, Pageable pageable);

    Page<Comment> findAllByBoardIdAndParentIsNull(Long boardId, Pageable pageable);
}
