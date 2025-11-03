package com.board.api.controller;

import com.board.api.dto.member.CreateMemberRequest;
import com.board.api.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="회원")
@RestController
@RequiredArgsConstructor
@RequestMapping("/_api/v1/member")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원 - 회원 생성")
    @PostMapping("")
    public ResponseEntity create(
            @RequestBody CreateMemberRequest createMemberRequest
            ){
        Long memberId = memberService.create(createMemberRequest);

        return ResponseEntity.ok().body(memberId);
    }

}
