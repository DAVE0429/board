package com.board.api.service;

import com.board.api.entity.Board;
import com.board.api.entity.Like;
import com.board.api.entity.Member;
import com.board.api.repository.BoardRepository;
import com.board.api.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {
    private final LikeRepository likeRepository;
    private final BoardRepository boardRepository;

    public Boolean create(Member member, Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("존재하지 않는 게시글 입니다."));
        Like like = Like.builder()
                .board(board)
                .member(member)
                .build();
        likeRepository.save(like);
        return true;
    }
}
