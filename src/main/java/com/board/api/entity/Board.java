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
    @JoinColumn(name ="category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_id")
    private Member member;

    @Builder
    public Board(String title, String content, Member member, Category category){
        this.title = title;
        this.content = content;
        this.member = member;
        this.category = category;
    }

    public void update(String title, String content,Category category){
        this.title = title;
        this.content = content;
        this.category = category;
    }
}

// 1 대 1 카테고리 crud 가능 - 게시글 - 카테고리의 게시글