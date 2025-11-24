package com.board.api.service;

import com.board.api.dto.like.LikeResponseDto;
import com.board.api.dto.like.LikerResponseDto;
import com.board.api.entity.Board;
import com.board.api.entity.Like;
import com.board.api.entity.Member;
import com.board.api.repository.BoardRepository;
import com.board.api.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {
    private final LikeRepository likeRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public LikeResponseDto toggleLike(Member member, Long boardId) {
        if(member == null){
            throw new RuntimeException("로그인이 필요합니다.");
        }
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        Optional<Like> existing = likeRepository.findByMemberAndBoard(member,board);

        if(existing.isPresent()){
            likeRepository.delete(existing.get());
            board.decreaseLike();
            return new LikeResponseDto(board.getLikeCount(), false);
        }

        Like like = new Like(member, board);
        likeRepository.save(like);
        board.increaseLike();

        return new LikeResponseDto(board.getLikeCount(), true);
    }

    public List<LikerResponseDto> getLikers(Long boardId){
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()-> new RuntimeException("게시글을 찾을 수 없습니다."));

        List<Like> likes = likeRepository.findByBoardId(boardId);

        return likes.stream().map(like -> {Member member = like.getMember();
            return new LikerResponseDto(member.getId(), member.getName());
        }).collect(Collectors.toList());
    }
}
