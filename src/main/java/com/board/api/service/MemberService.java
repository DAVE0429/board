package com.board.api.service;

import com.board.api.dto.member.CreateMemberRequest;
import com.board.api.dto.member.MemberResponse;
import com.board.api.entity.Address;
import com.board.api.entity.Member;
import com.board.api.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long create(CreateMemberRequest createMemberRequest){
        Member member = Member.builder()
                .name(createMemberRequest.getName())
                .email(createMemberRequest.getEmail())
                .address(new Address(createMemberRequest.getCity(), createMemberRequest.getStreet(), createMemberRequest.getZipcode()))
                .gender(createMemberRequest.getGender())
                .password(createMemberRequest.getPassword())
                .build();

        memberRepository.save(member);

        return member.getId();
    }

    // 사용자 조회
    public MemberResponse findMemberById(Long id){
        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        MemberResponse memberResponse = MemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .city(member.getAddress().getCity())
                .street(member.getAddress().getStreet())
                .zipcode(member.getAddress().getZipcode())
                .email(member.getEmail())
                .gender(member.getGender())
                .modifiedDate(member.getModifiedDate())
                .build();

        return memberResponse;
    }

    // 전체 사용자 조회
    public List<MemberResponse> findAllMember(){
        List<Member> members = memberRepository.findAll();
        List<MemberResponse> responseList = new ArrayList<>();
        for( Member member : members ){
            MemberResponse response =  MemberResponse.builder()
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

}
