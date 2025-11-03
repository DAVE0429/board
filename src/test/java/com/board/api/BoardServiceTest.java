package com.board.api;

import com.board.api.dto.board.CreateBoardRequest;
import com.board.api.dto.board.BoardResponse;
import com.board.api.dto.member.CreateMemberRequest;
import com.board.api.entity.Gender;
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
        CreateBoardRequest createBoardRequest = new CreateBoardRequest("originalTitle", "originalContent");

        //when
        // 게시글 등록
        Long boardId = boardService.createBoard(createBoardRequest,memberId);

        //then
        // 등록된 게시글 조회
        BoardResponse boardResponse = boardService.findBoardById(boardId);
        Assertions.assertEquals(boardResponse.getId(), boardId);
        Assertions.assertEquals(boardResponse.getTitle(), "originalTitle");
        Assertions.assertEquals(boardResponse.getContent(), "originalContent");
    }

    @Test
    @DisplayName("게시글 수정")
    public void updateBoard() {
        //given
        // 회원 생성
        Long memberId = createMember();
        // 게시글 생성
        CreateBoardRequest createBoardRequest = new CreateBoardRequest("originalTitle", "originalContent");
        // 게시글 등록
        Long boardId = boardService.createBoard(createBoardRequest, memberId);
        // 수정할 게시글 생성
        CreateBoardRequest modifiedBoardFormDto = new CreateBoardRequest("modifiedTitle", "modifiedContent");

        //when
        // 게시글 수정
        Long updateBoardId = boardService.updateBoard(modifiedBoardFormDto,boardId);

        //then
        // 수정 후 게시글 조회
        BoardResponse boardResponse = boardService.findBoardById(boardId);
        Assertions.assertNotEquals("originalTitle", boardResponse.getTitle());
        Assertions.assertNotEquals("originalContent", boardResponse.getContent());

    }

    @Test
    @DisplayName("게시글 삭제")
    public void deleteBoard() {

        // 회원 생성
        Long memberId = createMember();
        // 게시글 생성
        CreateBoardRequest createBoardRequest = new CreateBoardRequest("originalTitle", "originalContent");
        // 게시글 등록
        Long boardId = boardService.createBoard(createBoardRequest, memberId);


        // 게시글 삭제
        boardService.deleteBoard(boardId);


        // 삭제된 게시글 조회
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            boardService.findBoardById(boardId);
        });

        // 예외 메시지 출력
        System.out.println("예외 메시지: " + exception.getMessage());

    }


    public Long createMember(){
        CreateMemberRequest createMemberRequest = CreateMemberRequest.builder()
                .name("test")
                .email("test@test.com")
                .gender(Gender.M)
                .city("city")
                .street("street")
                .zipcode("zipcode")
                .password("test1234")
                .build();

        return memberService.create(createMemberRequest);
    }

}
