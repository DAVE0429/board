package com.board.api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "bookmark",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"member_id", "board_id"}
                )
        }
)
@Entity
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public Bookmark(Member member, Board board){
        this.member = member;
        this.board = board;
    }
}
