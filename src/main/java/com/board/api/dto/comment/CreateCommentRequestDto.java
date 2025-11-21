package com.board.api.dto.comment;

import com.board.api.dto.category.CreateCategoryRequestDto;
import com.board.api.entity.Board;
import com.board.api.entity.Comment;
import com.board.api.entity.Member;
import lombok.Getter;

@Getter
public class CreateCommentRequestDto {

    private String content;

    public CreateCommentRequestDto(String content){
        this.content = content;
    }

    public Comment toEntity(Member member, Board board){
        return Comment.builder()
                .member(member)
                .board(board)
                .content(this.content)
                .build();
    }


}

