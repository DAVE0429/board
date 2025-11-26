package com.board.api.dto.member;

import com.board.api.enums.Gender;
import com.board.api.entity.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberResponseDto {
    private Long id;
    private String email;
    private String name;
    private String city;
    private String street;
    private String zipcode;
    private Gender gender;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss")
    private LocalDateTime createdDate;
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss")
    private LocalDateTime modifiedDate;

    @Builder
    public MemberResponseDto(Long id
            , String email
            , String name
            , String city
            , String street
            , String zipcode
            , Gender gender
            , LocalDateTime createdDate
            , LocalDateTime modifiedDate){

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

    public static MemberResponseDto from(Member member){
        return MemberResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .city(member.getAddress().getCity())
                .street(member.getAddress().getStreet())
                .zipcode(member.getAddress().getZipcode())
                .gender(member.getGender())
                .createdDate(member.getCreatedDate())
                .modifiedDate(member.getModifiedDate())
                .build();
    }

}
