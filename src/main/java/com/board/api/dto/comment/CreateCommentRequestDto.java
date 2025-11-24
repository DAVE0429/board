package com.board.api.dto.comment;

import com.board.api.dto.category.CreateCategoryRequestDto;
import com.board.api.entity.Board;
import com.board.api.entity.Comment;
import com.board.api.entity.Member;
import lombok.Getter;

@Getter
public class CreateCommentRequestDto {
    private Long boardId;
    private Long parentId;
    private String content;

    public CreateCommentRequestDto(Long boardId, Long parentId, String content){
        this.boardId = boardId;
        this.parentId = parentId;
        this.content = content;
    }

    public Comment toEntity(Member member, Board board, Comment parentComment){
        return Comment.builder()
                .member(member)
                .board(board)
                .content(this.content)
                .parent(parentComment)
                .build();
    }


}

