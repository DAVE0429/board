package com.board.api.dto.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginMemberRequestDto {
    private String email;
    private String password;
}
