package com.board.api.dto.like;

import com.board.api.enums.TargetType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeRequestDto {
    private Long targetId;
    private TargetType targetType;
}
