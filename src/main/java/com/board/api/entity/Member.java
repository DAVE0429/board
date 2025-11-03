package com.board.api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="member")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String password;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private List<Board> boards = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Gender gender;

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
}
