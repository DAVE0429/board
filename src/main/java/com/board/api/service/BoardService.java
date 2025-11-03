package com.board.api.service;

import com.board.api.dto.board.CreateBoardRequest;
import com.board.api.dto.board.BoardResponse;
import com.board.api.entity.Board;
import com.board.api.entity.Member;
import com.board.api.repository.BoardRepository;
import com.board.api.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    // 게시글 등록
    @Transactional
    public Long createBoard(CreateBoardRequest createBoardRequest, Long id){
        Member member = memberRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        Board board = Board.builder()
                .title(createBoardRequest.getTitle())
                .content(createBoardRequest.getContent())
                .member(member)
                .build();

        boardRepository.save(board);

        return board.getId();
    }

    // 게시글 수정
    @Transactional
    public Long updateBoard(CreateBoardRequest createBoardRequest, Long boardId){
        Board board = boardRepository.findById(boardId).orElseThrow(()-> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        board.update(createBoardRequest.getTitle(), createBoardRequest.getContent());


        return boardId;
    }

    // 게시글 삭제
    @Transactional
    public Long deleteBoard(Long boardId){
        // 혹시 없는 게시글이라면 예외 발생
        if (!boardRepository.existsById(boardId)) {
            throw new IllegalArgumentException("삭제할 게시글이 존재하지 않습니다.");
        }

        // 삭제 수행
        boardRepository.deleteById(boardId);

        // 삭제된 게시글의 id 반환
        return boardId;
    }

    // 게시글 조회
    public BoardResponse findBoardById(Long boardId){
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + boardId));

        return BoardResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .author(board.getMember().getName())
                .modifiedDate(board.getModifiedDate())
                .build();
    }

    // 게시글 전체 조회
    public Page<BoardResponse> findAllBoards(Pageable pageable) {
        try {
            Page<Board> boardPage = boardRepository.findAllWithMember(pageable);

            if (boardPage.isEmpty()) {
                throw new IllegalStateException("등록된 게시글이 없습니다.");
            }

            return boardPage.map(board -> BoardResponse.builder()
                    .id(board.getId())
                    .title(board.getTitle())
                    .author(board.getMember().getName())
                    .content(board.getContent())
                    .createdDate(board.getCreatedDate())
                    .build()
            );

        } catch (DataAccessException e) {
            throw new RuntimeException("데이터베이스 접근 중 오류가 발생했습니다.", e);
        } catch (Exception e) {
            throw new RuntimeException("게시글 조회 중 오류가 발생했습니다.", e);
        }
    }

    // 검색 기능
    @Transactional
    public Page<BoardResponse> search(String keyword, Pageable pageable){
        Page<Board> boardPage = boardRepository.findByTitleContaining(keyword, pageable);

        return boardPage.map(board -> BoardResponse.builder()
                        .id(board.getId())
                        .title(board.getTitle())
                        .author(board.getMember().getName())
                        .content(board.getContent())
                        .createdDate(board.getCreatedDate())
                        .build());
    }


}
