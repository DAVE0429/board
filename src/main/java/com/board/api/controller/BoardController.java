package com.board.api.controller;

import com.board.api.dto.board.BoardResponse;
import com.board.api.dto.board.CreateBoardRequest;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="게시판")
@RestController
@RequiredArgsConstructor
@RequestMapping("/_api/v1/board")
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "게시판 - 게시판 생성")
    @PostMapping("")
    public ResponseEntity create(
            @RequestBody CreateBoardRequest createBoardRequest
            ,Long memberId
            ){
        Long boardId = boardService.createBoard(createBoardRequest, memberId);
        return ResponseEntity.ok().body(boardId);
    }

    @Operation(summary = "게시판 - 전체 게시글 조회 (페이징 포함)")
    @GetMapping("")
    @PageableAsQueryParam
    public ResponseEntity<Page<BoardResponse>> findAllBoards(
            @ParameterObject @PageableDefault( size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
            ){
        Page<BoardResponse> result = boardService.findAllBoards(pageable);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "게시판 - 게시글 검색")
    @GetMapping("/search")
    public ResponseEntity<Page<BoardResponse>> search(
            @ParameterObject
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)Pageable pageable
            ,@RequestParam String keyword){


        Page<BoardResponse> searchResult = boardService.search(keyword,pageable);

        return ResponseEntity.ok(searchResult);
    }

    @Operation(summary = "게시판 - 게시판 수정")
    @PutMapping("{id}")
    public ResponseEntity update(
            @PathVariable("id") Long id,
            @RequestBody CreateBoardRequest updateBoardRequestDto
    ){
        Long boardId = boardService.updateBoard(updateBoardRequestDto,id);
        return ResponseEntity.ok().body(boardId);
    }

    @Operation(summary = "게시판 - 게시판 삭제")
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        Long boardId = boardService.deleteBoard(id);
        return ResponseEntity.ok().body(boardId);
    }

}
