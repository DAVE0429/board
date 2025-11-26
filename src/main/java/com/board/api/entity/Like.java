package com.board.api.entity;

import com.board.api.enums.TargetType;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "likes")
@Entity
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_id", nullable = false)
    private Member member;

    @Column( name="target_id", nullable = false)
    private Long targetId;

    @Enumerated(EnumType.STRING)
    @Column(name = "like_type", nullable = false)
    private TargetType targetType; // BOARD, COMMENT


    @Builder
    public Like(Member member, Long targetId, TargetType targetType) {
        this.member = member;
        this.targetId = targetId;
        this.targetType = targetType;
    }
}
