package com.board.api.entity;

import com.board.api.enums.NotificationType;
import com.board.api.enums.TargetType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    private TargetType targetType;

    private Long targetId;

    private String message;

    private boolean isRead = false;

    private LocalDateTime createdAt = LocalDateTime.now();

    public static Notification create(
            Member receiver,
            Member sender,
            NotificationType type,
            TargetType targetType,
            Long targetId,
            String message
    ){
        Notification n = new Notification();
        n.receiver = receiver;
        n.sender = sender;
        n.type = type;
        n.targetType = targetType;
        n.targetId = targetId;
        n.message = message;
        return n;
    }

    public void read(){
        this.isRead = true;
    }
}
