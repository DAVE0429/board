package com.board.api.repository;

import com.board.api.dto.board.BoardResponse;
import com.board.api.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @EntityGraph(attributePaths = {"member"})
    @Query("SELECT board FROM Board board")
    Page<Board> findAllWithMember(Pageable pageable);

    @EntityGraph(attributePaths = {"member"})
    @Query("SELECT board FROM Board board WHERE board.id = :id")
    Optional<Board> findOneWithMemberById(@Param("id") Long id);


    // 게시글 제목에 키워드가 포함된 게시글을 검색 + Member를 함께 fetch (N+1 방지)
//    @Query("SELECT board FROM Board board JOIN FETCH board.member WHERE board.title LIKE %:keyword%")
    @EntityGraph(attributePaths = {"member"})
    Page<Board> findByTitleContaining(String keyword, Pageable pageable);

    @Query("SELECT board FROM Board board JOIN FETCH board.member")
    List<Board> findAllPostList();

    @Query("select board from Board board join fetch board.member boardMember where board.id = :id")
    Board findOnePostById(Long id);
}
