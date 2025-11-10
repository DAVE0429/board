package com.board.api.service;

import com.board.api.config.JwtTokenProvider;
import com.board.api.dto.member.CreateMemberRequestDto;
import com.board.api.dto.member.LoginMemberRequestDto;
import com.board.api.dto.member.MemberResponseDto;
import com.board.api.entity.Address;
import com.board.api.entity.Member;
import com.board.api.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public MemberResponseDto create(CreateMemberRequestDto createMemberRequestDto){
        String encodedPassword = passwordEncoder.encode(createMemberRequestDto.getPassword());
        Member member = Member.builder()
                .name(createMemberRequestDto.getName())
                .email(createMemberRequestDto.getEmail())
                .address(new Address(createMemberRequestDto.getCity(), createMemberRequestDto.getStreet(), createMemberRequestDto.getZipcode()))
                .gender(createMemberRequestDto.getGender())
                .password(encodedPassword)
                .build();

        memberRepository.save(member);

        return MemberResponseDto.from(member);
    }

    // 사용자 조회
    public MemberResponseDto findMemberById(Long id){
        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        MemberResponseDto memberResponseDto = MemberResponseDto.builder()
                .id(member.getId())
                .name(member.getName())
                .city(member.getAddress().getCity())
                .street(member.getAddress().getStreet())
                .zipcode(member.getAddress().getZipcode())
                .email(member.getEmail())
                .gender(member.getGender())
                .modifiedDate(member.getModifiedDate())
                .build();

        return memberResponseDto;
    }

    // 전체 사용자 조회
    public List<MemberResponseDto> findAllMember(){
        List<Member> members = memberRepository.findAll();
        List<MemberResponseDto> responseList = new ArrayList<>();
        for( Member member : members ){
            MemberResponseDto response =  MemberResponseDto.builder()
                    .id(member.getId())
                    .name(member.getName())
                    .city(member.getAddress().getCity())
                    .street(member.getAddress().getStreet())
                    .zipcode(member.getAddress().getZipcode())
                    .modifiedDate(member.getModifiedDate())
                    .build();

            responseList.add(response);
        }

        return responseList;
    }

    // 회원 탈퇴
    @Transactional
    public void deleteMember(Long id){
        Member member = memberRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("회원을 찾을 수 없습니다."));
        memberRepository.delete(member);
    }

    public String login(LoginMemberRequestDto loginMemberRequestDto) {
        Member member = memberRepository.findByEmail(loginMemberRequestDto.getEmail()).orElseThrow(() -> new RuntimeException("찾을수 없는 유저입니다."));

        if(!passwordEncoder.matches(loginMemberRequestDto.getPassword(), member.getPassword())) {
            throw new RuntimeException("찾을수 없는 유저입니다 이메일이나 비밀번호를 다시 확인해주세요");
        }

        return jwtTokenProvider.createToken(member.getEmail());
    }
}
