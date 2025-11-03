package com.board.api;

import com.board.api.entity.Address;
import com.board.api.entity.Gender;
import com.board.api.entity.Member;
import com.board.api.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    // 회원 정보 생성
    @Test
    void saveMember() {
        Member saveParams = Member.builder()
                .email("aaa@aaa.com")
                .password("1234")
                .name("당근")
                .address(new Address("서울시","땡땡땡","1234"))
                .gender(Gender.M)
                .build();
        Member member = memberRepository.save(saveParams);
        Assertions.assertEquals(member.getEmail(),"aaa@aaa.com");
    }

    // 전체 회원 조회
    @Test
    void findAllMember() {
        System.out.println(memberRepository.findAll());
    }

    // 회원 상세정보 조회
    @Test
    void findMemberById(){
        Member member = memberRepository.findById(2L).orElseThrow(() -> new EntityNotFoundException("해당 회원이 없습니다."));
        Assertions.assertEquals(member.getEmail(),"aaa@aaa.com");
        System.out.println(member);
    }

    // 회원 정보 삭제
    @Test
    void deleteMemberById(){
        memberRepository.deleteById(1L);
    }
}
