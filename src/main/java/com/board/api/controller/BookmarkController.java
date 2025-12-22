package com.board.api.controller;

import com.board.api.dto.board.BoardResponseDto;
import com.board.api.entity.Board;
import com.board.api.entity.Member;
import com.board.api.service.BookmarkService;
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
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name="북마크")
@RestController
@RequiredArgsConstructor
@RequestMapping("/_api/v1/bookmark")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @Operation(summary = "북마크 - 북마크 생성")
    @PostMapping("/boards/{boardId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity bookmark(
            @AuthenticationPrincipal Member member,
            @PathVariable Long boardId
    ){
        boolean bookmarked = bookmarkService.addBookmark(member,boardId);
        return ResponseEntity.ok().body(Map.of("bookmarked",bookmarked));
    }

    @Operation(summary = "북마크 - 북마크 삭제")
    @DeleteMapping("/boards/{boardId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity unBookmark(
            @AuthenticationPrincipal Member member,
            @PathVariable Long boardId
    ){
        boolean unBookmarked = bookmarkService.removeBookmark(member, boardId);
        return ResponseEntity.ok().body(Map.of("unBookmarked", unBookmarked));
    }

    @Operation(summary = "북마크 - 북마크 여부 확인")
    @GetMapping("/boards/{boardId}/exists")
    public ResponseEntity<Boolean> isBookmarked(
            @AuthenticationPrincipal Member member,
            @PathVariable Long boardId
    ){
        return ResponseEntity.ok(
                bookmarkService.isBookmarked(member, boardId)
        );
    }

    @Operation(summary = "북마크 - 유저가 북마크한 갯수 확인")
    @GetMapping("/board/{boardId}/count")
    public ResponseEntity<Long> bookmarkCount(
            @PathVariable Long boardId
    ){
        return ResponseEntity.ok(
                bookmarkService.getBookmarkCount(boardId)
        );
    }

    @Operation(summary = "북마크 - 내가 북마크한 게시글")
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    @PageableAsQueryParam
    public ResponseEntity<Page<BoardResponseDto>> myBookmarks(
            @AuthenticationPrincipal Member member,
            @ParameterObject @PageableDefault( size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<BoardResponseDto> result = bookmarkService.getMyBookmarks(member,pageable);
        return ResponseEntity.ok(result);
    }
}
