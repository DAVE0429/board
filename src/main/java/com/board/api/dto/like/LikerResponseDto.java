package com.board.api.dto.like;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikerResponseDto {
    private Long memberId;
    private String name;
}
