package com.board.api.dto.comment;

import com.board.api.dto.board.BoardResponseDto;
import com.board.api.dto.member.MemberResponseDto;
import com.board.api.entity.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CommentResponseDto {
    private Long id;
    private MemberResponseDto member;

    private String content;

    private boolean deleted;

    private List<CommentResponseDto> children;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    @Builder
    public CommentResponseDto(Long id, MemberResponseDto member, boolean deleted, String content, List<CommentResponseDto> children, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.member = member;
        this.content = content;
        this.deleted = deleted;
        this.children = children;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }




    public static CommentResponseDto from(Comment comment) {
        MemberResponseDto member = MemberResponseDto.from(comment.getMember());
        return CommentResponseDto.builder()
                .id(comment.getId())
                .member(member)
                .deleted(comment.isDeleted())
                .content(comment.getContent())
                .children(comment.getChildren() == null ? new ArrayList<>() :
                        comment.getChildren().stream()
                        .map(CommentResponseDto::from)
                        .collect(Collectors.toList()))
                .createdDate(comment.getCreatedDate())
                .modifiedDate(comment.getModifiedDate())
                .build();
    }


}
