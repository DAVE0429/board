package com.board.api.dto.member;

import com.board.api.entity.Gender;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberResponse {
    private Long id;
    private String email;
    private String name;
    private String city;
    private String street;
    private String zipcode;
    private Gender gender;
    private String createdDate;
    private String modifiedDate;

    @Builder
    public MemberResponse(Long id
            , String email
            , String name
            , String city
            , String street
            , String zipcode
            , Gender gender
            , String createdDate
            , String modifiedDate){

        this.id = id;
        this.email = email;
        this.name = name;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
        this.gender = gender;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }


}
