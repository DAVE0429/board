package com.board.api.dto.comment;

import com.board.api.dto.member.MemberResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CommentResponseDto {
    private Long id;
    private MemberResponseDto member;

    private String content;

    private boolean deleted;

    private List<CommentResponseDto> children;

    private Long likeCount;

    private boolean liked;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    @Builder
    public CommentResponseDto(Long id, MemberResponseDto member, boolean deleted, String content, List<CommentResponseDto> children, Long likeCount, boolean liked, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.member = member;
        this.content = content;
        this.deleted = deleted;
        this.children = children;
        this.likeCount = likeCount;
        this.liked = liked;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }



}
