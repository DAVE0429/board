package com.board.api.service;

import com.board.api.dto.board.CreateBoardRequestDto;
import com.board.api.dto.board.BoardResponseDto;
import com.board.api.dto.board.UpdateBoardRequestDto;
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


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    // 게시글 등록
    @Transactional
    public BoardResponseDto createBoard(CreateBoardRequestDto createBoardRequestDto, Member member){

        Board board = Board.builder()
                .title(createBoardRequestDto.getTitle())
                .content(createBoardRequestDto.getContent())
                .member(member)
                .build();

        boardRepository.save(board);

        return BoardResponseDto.from(board);
    }

    // 게시글 수정
    @Transactional
    public BoardResponseDto updateBoard(UpdateBoardRequestDto updateBoardRequestDto, Long id, Member member){
        Board board = boardRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        if (!board.getMember().getId().equals(member.getId())) {
            throw new RuntimeException("본인이 작성한 글만 수정할 수 있습니다.");
        }

        board.update(updateBoardRequestDto.getTitle(), updateBoardRequestDto.getContent());


        return BoardResponseDto.from(board);
    }

    // 게시글 삭제
    @Transactional
    public Long deleteBoard(Long id, Member member){
        // 혹시 없는 게시글이라면 예외 발생
       Board board = boardRepository.findById(id)
               .orElseThrow(()-> new RuntimeException("게시글을 찾을 수 없습니다."));

        if (!board.getMember().getId().equals(member.getId())) {
            throw new RuntimeException("본인이 작성한 글만 삭제할 수 있습니다.");
        }

        // 삭제 수행
        boardRepository.deleteById(id);

        // 삭제된 게시글의 id 반환
        return id;
    }

    // 게시글 조회
    public BoardResponseDto findBoardById(Long id){
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));

        return BoardResponseDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .author(board.getMember().getName())
                .modifiedDate(board.getModifiedDate())
                .build();
    }

    // 게시글 전체 조회
    public Page<BoardResponseDto> findAllBoards(Pageable pageable) {
        try {
            Page<Board> boardPage = boardRepository.findAll(pageable);

            if (boardPage.isEmpty()) {
                throw new IllegalStateException("등록된 게시글이 없습니다.");
            }

            return boardPage.map(board -> BoardResponseDto.builder()
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
    public Page<BoardResponseDto> search(String keyword, Pageable pageable){
        Page<Board> boardPage = boardRepository.findByTitleContaining(keyword, pageable);
        return boardPage.map(board -> BoardResponseDto.builder()
                        .id(board.getId())
                        .title(board.getTitle())
                        .author(board.getMember().getName())
                        .content(board.getContent())
                        .createdDate(board.getCreatedDate())
                        .build());
    }


}
