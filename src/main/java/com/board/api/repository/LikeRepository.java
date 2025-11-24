package com.board.api.repository;

import com.board.api.entity.Board;
import com.board.api.entity.Like;
import com.board.api.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Long countByBoard(Board board);
    boolean existsByMemberAndBoard(Member member, Board board);
    Optional<Like> findByMemberAndBoard(Member member, Board board);
    List<Like> findByBoardId(Long boardId);
}
