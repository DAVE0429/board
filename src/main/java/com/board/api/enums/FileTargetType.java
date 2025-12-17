package com.board.api.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileTargetType {
    BOARD("boards"),
    COMMENT("comments"),
    PROFILE("profiles");

    private final String dir;

}
