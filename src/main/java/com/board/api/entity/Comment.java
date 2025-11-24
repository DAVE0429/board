package com.board.api.entity;

import com.board.api.dto.comment.UpdateCommentRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "comment")
@Entity
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private List<Comment> children = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="board_id")
    private Board board;

    private String content;

    private boolean deleted = false;

    public void setDeleted(boolean deleted){
        this.deleted = deleted;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setMember(Member member){
        this.member = member;
    }

    @Builder
    public Comment(Member member, Board board, String content, Comment parent) {
        this.member = member;
        this.board = board;
        this.content = content;
        this.parent = parent;
    }

    public Comment update(UpdateCommentRequestDto updateCommentRequestDto) {
        this.content = updateCommentRequestDto.getContent();
        return this;
    }
}

