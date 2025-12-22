package com.board.api.repository;

import com.board.api.dto.board.BoardResponseDto;
import com.board.api.entity.Board;
import com.board.api.entity.Bookmark;
import com.board.api.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    boolean existsByMemberAndBoard(Member member, Board board);
    long countByBoard(Board board);
    Optional<Bookmark> findByMemberAndBoard(Member member, Board board);
    @Query("""
       select b.board
       from Bookmark b
       where b.member = :member
       """)
    Page<Board> findBookmarkedBoardsByMember(Member member, Pageable pageable);
}
