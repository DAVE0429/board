package com.board.api.controller;

import com.board.api.dto.board.BoardResponseDto;
import com.board.api.dto.board.CreateBoardRequestDto;
import com.board.api.dto.board.UpdateBoardRequestDto;
import com.board.api.entity.Member;
import com.board.api.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name="게시판")
@RestController
@RequiredArgsConstructor
@RequestMapping("/_api/v1/board")
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "게시판 - 게시판 생성")
    @PreAuthorize("isAuthenticated()")
    @PostMapping("")
    public ResponseEntity create(
            @AuthenticationPrincipal Member member,
            @RequestBody CreateBoardRequestDto createBoardRequestDto

            ){
        if(member == null) {
            throw new RuntimeException("로그인한 사용자 정보가 없습니다.");
        }
        BoardResponseDto boardResponseDto = boardService.createBoard(createBoardRequestDto, member);
        return ResponseEntity.ok().body(boardResponseDto);
    }

    @Operation(summary = "게시판 - 전체 게시글 조회 (페이징 포함)")
    @GetMapping("")
    @PageableAsQueryParam
    public ResponseEntity<Page<BoardResponseDto>> findAllBoards(
            @AuthenticationPrincipal Member member,
            @ParameterObject @PageableDefault( size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
            ){
        Page<BoardResponseDto> result = boardService.findAllBoards(member, pageable);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "게시판 - 게시글 검색")
    @GetMapping("/search")
    public ResponseEntity<Page<BoardResponseDto>> search(
            @AuthenticationPrincipal Member member,
            @ParameterObject
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)Pageable pageable
            ,@RequestParam String keyword){


        Page<BoardResponseDto> searchResult = boardService.search(member, keyword,pageable);

        return ResponseEntity.ok(searchResult);
    }

    @Operation(summary = "게시판 - 게시판 수정")
    @PreAuthorize("isAuthenticated()")
    @PutMapping("{id}")
    public ResponseEntity update(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal Member member,
            @RequestBody UpdateBoardRequestDto updateBoardRequestDto
    ){
        if (member == null) {
            throw new RuntimeException("로그인한 사용자 정보가 없습니다.");
        }
        BoardResponseDto boardResponseDto = boardService.updateBoard(updateBoardRequestDto,id,member);
        return ResponseEntity.ok().body(boardResponseDto);
    }

    @Operation(summary = "게시판 - 카테고리 별 게시글 조회")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<BoardResponseDto>> findAllByCategory(
            @AuthenticationPrincipal Member member,
            @PathVariable Long categoryId,
            @ParameterObject @PageableDefault( size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<BoardResponseDto> boards = boardService.findBoardsByCategory(member, pageable,categoryId);
        return ResponseEntity.ok(boards);
    }

    @Operation(summary = "게시글 - 게시글 한개 조회")
    @GetMapping("{id}")
    public ResponseEntity findById(@AuthenticationPrincipal Member member, @PathVariable("id") Long id){
        BoardResponseDto result = boardService.findBoardById(member, id);
        return ResponseEntity.ok().body(result);
    }

    @Operation(summary = "게시판 - 게시판 삭제")
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id,
                                 @AuthenticationPrincipal Member member){
        if (member == null) {
            throw new RuntimeException("로그인한 사용자 정보가 없습니다.");
        }
        Long boardId = boardService.deleteBoard(id,member);
        return ResponseEntity.ok().body(boardId);
    }

}
// bearer cors security 흐름 board 기능 정리