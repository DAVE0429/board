package com.board.api.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name="board")
@Entity
@ToString
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_id")
    private Member member;

    @Builder
    public Board(String title, String content, Member member){
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}

// 1 대 1 카테고리 crud 가능 - 게시글 - 카테고리의 게시글