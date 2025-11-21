package com.board.api.dto.comment;

import com.board.api.dto.board.BoardResponseDto;
import com.board.api.dto.member.MemberResponseDto;
import com.board.api.entity.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;
    private MemberResponseDto member;

    private String content;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    @Builder
    public CommentResponseDto(Long id, MemberResponseDto member, String content, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.member = member;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }




    public static CommentResponseDto from(Comment comment) {
        MemberResponseDto member = MemberResponseDto.from(comment.getMember());
        return CommentResponseDto.builder()
                .id(comment.getId())
                .member(member)
                .content(comment.getContent())
                .build();
    }


}
