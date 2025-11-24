package com.board.api.dto.comment;

import com.board.api.entity.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentDeleteResponseDto {
    private Long id;
    private boolean softDelete;
    private String message;

    public static CommentDeleteResponseDto soft(Long commentId){
        return CommentDeleteResponseDto.builder()
                .id(commentId)
                .softDelete(true)
                .message("자식 댓글이 있어 부분 삭제 되었습니다.")
                .build();
    }

    public static CommentDeleteResponseDto hard(Long commentId){
        return CommentDeleteResponseDto.builder()
                .id(commentId)
                .softDelete(false)
                .message("댓글이 완전히 삭제 되었습니다.")
                .build();
    }
}
