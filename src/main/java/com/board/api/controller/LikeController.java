package com.board.api.controller;

import com.board.api.dto.like.LikeRequestDto;
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
import java.util.Map;

@Tag(name = "좋아요")
@RestController
@RequiredArgsConstructor
@RequestMapping("/_api/v1/like")
public class LikeController {

    private final LikeService likeService;

    @Operation(summary = "좋아요 - 좋아요 기능")
    @PostMapping("")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity like(
            @RequestBody LikeRequestDto requestDto,
            @AuthenticationPrincipal Member member
            ){
        boolean liked = likeService.like(member, requestDto);
        return ResponseEntity.ok().body(Map.of("liked",liked));
    }

    @Operation(summary = "좋아요 - 좋아요 취소 기능")
    @DeleteMapping("")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity unlike(
            @RequestBody LikeRequestDto requestDto,
            @AuthenticationPrincipal Member member
    ){
        boolean unliked = likeService.unLike(member, requestDto);
        return ResponseEntity.ok().body(Map.of("unliked",unliked));
    }

    @Operation(summary = "좋아요 - 게시글에 좋아요한 사람 목록 조회")
    @GetMapping("/{boardId}/likers")
    public ResponseEntity<List<LikerResponseDto>> getLikers(@PathVariable Long boardId){
        List<LikerResponseDto> response = likeService.getLikers(boardId);
        return ResponseEntity.ok(response);
    }
}
