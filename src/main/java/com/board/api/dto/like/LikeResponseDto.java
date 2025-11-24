package com.board.api.dto.like;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LikeResponseDto {
    private Long likeCount; // 현재 누적 좋아요 수
    private boolean liked; // 내가 좋아요 했는지 여부
}

