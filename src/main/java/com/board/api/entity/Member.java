package com.board.api.entity;

import com.board.api.dto.member.CreateMemberRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="member")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String name;

    @Embedded
    private Address address;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Board> boards = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Comment> Comments = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Gender gender;

    public Member toEntity(CreateMemberRequestDto createMemberRequestDto) {
        this.email = createMemberRequestDto.getEmail();

        return this;
    }

    @Builder
    public Member(String email, String password, String name, Address address, Gender gender){
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.gender = gender;
    }

    public void update(String email, String password, String name, Address address){
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return id.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
