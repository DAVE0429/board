package com.board.api.repository;

import com.board.api.entity.Board;
import com.board.api.entity.Like;
import com.board.api.entity.Member;
import com.board.api.enums.TargetType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Long countByTargetTypeAndTargetId(TargetType targetType, Long targetId);
    boolean existsByMemberAndTargetTypeAndTargetId(Member member, TargetType targetType, Long targetId);
    Optional<Like> findByMemberAndTargetIdAndTargetType(Member member, Long targetId, TargetType targetType);
    List<Like> findByTargetTypeAndTargetId(TargetType targetType, Long targetId);
}
