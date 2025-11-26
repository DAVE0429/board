package com.board.api.controller;

import com.board.api.dto.comment.CommentDeleteResponseDto;
import com.board.api.dto.comment.CommentResponseDto;
import com.board.api.dto.comment.CreateCommentRequestDto;
import com.board.api.dto.comment.UpdateCommentRequestDto;
import com.board.api.entity.Member;
import com.board.api.service.CommentService;
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

@Tag(name = "댓글")
@RestController
@RequiredArgsConstructor
@RequestMapping("/_api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 - 댓글 생성")
    @PreAuthorize("isAuthenticated()")
    @PostMapping("")
    public ResponseEntity create(@AuthenticationPrincipal Member member,
                                 @RequestBody CreateCommentRequestDto requestDto){
        if(member == null){
            throw new RuntimeException("로그인한 사용자 정보가 없습니다.");
        }

        CommentResponseDto response = commentService.create(member,requestDto);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "댓글 - 모든 댓글 불러오기")
    @GetMapping("{id}")
    @PageableAsQueryParam
    public ResponseEntity<Page<CommentResponseDto>> findAllCommentByBoard(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal Member member,
            @ParameterObject @PageableDefault(size = 10, direction = Sort.Direction.DESC)Pageable pageable){
        Page<CommentResponseDto> result = commentService.findAll(id,member,pageable);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "댓글 - 내가 작성한 댓글 조회하기")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public ResponseEntity<Page<CommentResponseDto>> getMyComments(
            @AuthenticationPrincipal Member member,
            @ParameterObject @PageableDefault(size = 10, direction = Sort.Direction.DESC) Pageable pageable){
        Page<CommentResponseDto> response = commentService.findMyComments(member, pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "댓글 - 댓글 수정")
    @PreAuthorize("isAuthenticated()")
    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @AuthenticationPrincipal Member member,@RequestBody UpdateCommentRequestDto updateCommentRequestDto){
        if(member == null){
            throw new RuntimeException("로그인한 사용자 정보가 없습니다,");
        }
        CommentResponseDto response = commentService.update(member,id,updateCommentRequestDto);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "댓글 - 댓글 삭제")
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") Long id, @AuthenticationPrincipal Member member){
        CommentDeleteResponseDto response = commentService.delete(id);
        return ResponseEntity.ok().body(response);
    }

}
