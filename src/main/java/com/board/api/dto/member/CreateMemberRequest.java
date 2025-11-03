package com.board.api.dto.member;

import com.board.api.entity.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateMemberRequest {
    @Email
    @NotEmpty(message = "이메일을 입력해주세요.")
    private String email;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotEmpty(message = "이름을 입력해주세요.")
    private String name;

    @NotEmpty(message = "주소를 입력해주세요.")
    private String city;

    @NotEmpty(message = "주소를 입력해주세요.")
    private String street;

    @NotEmpty(message = "주소를 입력해주세요.")
    private String zipcode;

    @NotEmpty(message = "성별을 입력해주세요.")
    private Gender gender;

    @Builder
    public CreateMemberRequest(String email, String password, String name, String city, String street, String zipcode, Gender gender){
        this.email = email;
        this.password = password;
        this.name = name;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
        this.gender = gender;
    }
}
