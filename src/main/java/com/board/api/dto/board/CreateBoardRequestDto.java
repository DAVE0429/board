package com.board.api.dto.board;

import com.board.api.entity.Board;
import com.board.api.entity.Category;
import com.board.api.entity.Member;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;


@Getter
public class CreateBoardRequestDto {

    @NotEmpty(message ="제목을 입력해주세요.")
    private String title;

    @NotEmpty(message = "게시글을 작성해주세요.")
    private String content;

    @NotEmpty(message = "카테고리를 설정해주세요.")
    private Long categoryId;


    public CreateBoardRequestDto(String title, String content, Long categoryId){
        this.title = title;
        this.content = content;
        this.categoryId = categoryId;
    }

    public Board toEntity(Member member,Category category){
        return Board.builder()
                .title(title)
                .content(content)
                .category(category)
                .member(member).build();
    }


}
