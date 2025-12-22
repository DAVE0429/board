package com.board.api.service;

import com.board.api.entity.Member;
import com.board.api.entity.Notification;
import com.board.api.enums.NotificationType;
import com.board.api.enums.TargetType;
import com.board.api.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    public void notify(
            Member receiver,
            Member sender,
            NotificationType type,
            TargetType targetType,
            Long targetId,
            String message
    ){
        Notification notification = Notification.create(receiver, sender, type, targetType, targetId, message);

        notificationRepository.save(notification);
    }

    public Page<Notification> getMyNotifications(Member member, Pageable pageable){
        return notificationRepository.findByReceiverOrderByCreatedAtDesc(member, pageable);
    }

//    @Transactional
//    public void read(Long notificationId, Member member){
//        Notification n = notificationRepository.findById(notificationId).orElseThrow(()-> new NotificationNotFoundException(notificationId));
//
//        if(!n.getReceiver().equals(member)){
//            throw new NotificationAccessDeniedException();
//        }
//
//        n.read();
//
//        return NotificationDto.from(notification);
//    }

}
