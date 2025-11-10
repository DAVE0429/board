package com.board.api.controller;

import com.board.api.dto.member.CreateMemberRequestDto;
import com.board.api.dto.member.LoginMemberRequestDto;
import com.board.api.dto.member.MemberResponseDto;
import com.board.api.entity.Member;
import com.board.api.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name="회원")
@RestController
@RequiredArgsConstructor
@RequestMapping("/_api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원 - 회원 생성")
    @PostMapping("")
    public ResponseEntity create(
            @RequestBody CreateMemberRequestDto createMemberRequestDto
            ){
        MemberResponseDto memberResponseDto = memberService.create(createMemberRequestDto);

        return ResponseEntity.ok().body(memberResponseDto);
    }

    @Operation(summary = "회원 - 로그인")
    @PostMapping("login")
    public ResponseEntity login(
            @RequestBody LoginMemberRequestDto loginMemberRequestDto
    ){
        String token = memberService.login(loginMemberRequestDto);

        return ResponseEntity.ok().body(token);
    }

    @Operation(summary = "회원 - 내 정보가져오기")
    @GetMapping("me")
    public ResponseEntity getMe(
            @AuthenticationPrincipal Member member
            ){

        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok().body(MemberResponseDto.from(member));
    }

}
