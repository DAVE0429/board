package com.board.api;

import com.board.api.dto.board.CreateBoardRequestDto;
import com.board.api.dto.board.BoardResponseDto;
import com.board.api.dto.member.CreateMemberRequestDto;
import com.board.api.entity.Gender;
import com.board.api.entity.Member;
import com.board.api.repository.MemberRepository;
import com.board.api.service.BoardService;
import com.board.api.service.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    BoardService boardService;

    @Test
    @DisplayName("게시글 등록")
    void createBoard() {
        //given
        // 회원 생성
        Long memberId = createMember();
        // 게시글 생성
        CreateBoardRequestDto createBoardRequestDto = new CreateBoardRequestDto("originalTitle", "originalContent");

        //when
        // 게시글 등록
        Long boardId = boardService.createBoard(createBoardRequestDto,memberId);

        //then
        // 등록된 게시글 조회
        BoardResponseDto boardResponseDto = boardService.findBoardById(boardId);
        Assertions.assertEquals(boardResponseDto.getId(), boardId);
        Assertions.assertEquals(boardResponseDto.getTitle(), "originalTitle");
        Assertions.assertEquals(boardResponseDto.getContent(), "originalContent");
    }

    @Test
    @DisplayName("게시글 수정")
    public void updateBoard() {
        //given
        // 회원 생성
        Long memberId = createMember();
        // 게시글 생성
        CreateBoardRequestDto createBoardRequestDto = new CreateBoardRequestDto("originalTitle", "originalContent");
        // 게시글 등록
        Long boardId = boardService.createBoard(createBoardRequestDto, memberId);
        // 수정할 게시글 생성
        CreateBoardRequestDto modifiedBoardFormDto = new CreateBoardRequestDto("modifiedTitle", "modifiedContent");

        //when
        // 게시글 수정
        BoardResponseDto boardResponseDto = boardService.updateBoard(modifiedBoardFormDto,boardId);

        Assertions.assertNotEquals("originalTitle", boardResponseDto.getTitle());
        Assertions.assertNotEquals("originalContent", boardResponseDto.getContent());

    }

    @Test
    @DisplayName("게시글 삭제")
    public void deleteBoard() {

        // 회원 생성
        Long memberId = createMember();
        // 게시글 생성
        CreateBoardRequestDto createBoardRequestDto = new CreateBoardRequestDto("originalTitle", "originalContent");
        // 게시글 등록
        BoardResponseDto boardResponseDto = boardService.createBoard(createBoardRequestDto, memberId);


        // 게시글 삭제
        boardService.deleteBoard(boardResponseDto.getId());


        // 삭제된 게시글 조회
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            boardService.findBoardById(boardResponseDto.getId());
        });

        // 예외 메시지 출력
        System.out.println("예외 메시지: " + exception.getMessage());

    }


    public Member createMember(){
        CreateMemberRequestDto createMemberRequestDto = CreateMemberRequestDto.builder()
                .name("test")
                .email("test@test.com")
                .gender(Gender.M)
                .city("city")
                .street("street")
                .zipcode("zipcode")
                .password("test1234")
                .build();

        return memberService.create(createMemberRequestDto);
    }

}
