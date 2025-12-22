package com.board.api.repository;

import com.board.api.entity.Member;
import com.board.api.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Page<Notification> findByReceiverOrderByCreatedAtDesc(Member receiver, Pageable pageable);

    long countByReceiverAndIsReadFalse(Member receiver);
}
