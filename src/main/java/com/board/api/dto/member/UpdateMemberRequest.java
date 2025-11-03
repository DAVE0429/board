package com.board.api.dto.member;

import lombok.Getter;

@Getter
public class UpdateMemberRequest {
    private String name;
    private String password;
    private String city;
    private String street;
    private String zipcode;
}
