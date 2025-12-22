package com.board.api.dto.notification;

import com.board.api.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class NotificationDto {
    private Long id;
    private String message;
    private boolean read;
    private LocalDateTime createdAt;

    public static NotificationDto from(Notification n){
        return new NotificationDto(
                n.getId(),
                n.getMessage(),
                n.isRead(),
                n.getCreatedAt()
        );
    }
}
