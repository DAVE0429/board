package com.board.api.controller;

import com.board.api.dto.like.LikeRequestDto;
import com.board.api.dto.like.LikeResponseDto;
import com.board.api.dto.like.LikerResponseDto;
import com.board.api.entity.Member;
import com.board.api.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "좋아요")
@RestController
@RequiredArgsConstructor
@RequestMapping("/_api/v1/like")
public class LikeController {

    private final LikeService likeService;

    @Operation(summary = "좋아요 - 좋아요 및 취소 기능")
    @PostMapping("")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity toggleLike(
            @RequestBody LikeRequestDto requestDto,
            @AuthenticationPrincipal Member member
            ){
        LikeResponseDto response = likeService.toggleLike(member, requestDto.getBoardId());
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "좋아요 - 게시글에 좋아요한 사람 목록 조회")
    @GetMapping("/{boardId}/likers")
    public ResponseEntity<List<LikerResponseDto>> getLikers(@PathVariable Long boardId){
        List<LikerResponseDto> response = likeService.getLikers(boardId);
        return ResponseEntity.ok(response);
    }
}
