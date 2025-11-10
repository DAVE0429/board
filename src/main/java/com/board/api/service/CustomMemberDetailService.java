package com.board.api.service;

import com.board.api.entity.Member;
import com.board.api.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomMemberDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if(optionalMember.isPresent()){
            return optionalMember.get();
        }
        throw new RuntimeException("찾을 수 없는 유저입니다.");
    }
}
