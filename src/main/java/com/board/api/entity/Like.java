package com.board.api.entity;

import com.board.api.enums.TargetType;
import jakarta.persistence.*;
import lombok.*;

@Table(
        name = "likes",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_likes_member_target",
                        columnNames = {"member_id", "target_id", "like_type"}
                )
        }
)
@Entity
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like extends BaseEntity {
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
