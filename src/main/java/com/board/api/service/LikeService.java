package com.board.api.service;

import com.board.api.dto.like.LikeRequestDto;
import com.board.api.dto.like.LikerResponseDto;
import com.board.api.entity.Board;
import com.board.api.entity.Like;
import com.board.api.entity.Member;
import com.board.api.enums.TargetType;
import com.board.api.repository.BoardRepository;
import com.board.api.repository.CommentRepository;
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
    private final CommentRepository commentRepository;

    @Transactional
    public boolean like(Member member, LikeRequestDto requestDto) {

        if(member == null){
            throw new RuntimeException("로그인이 필요합니다.");
        }

        validateTarget(requestDto);

        Optional<Like> existing = likeRepository.findByMemberAndTargetIdAndTargetType(member, requestDto.getTargetId(), requestDto.getTargetType());

        if(existing.isPresent()){
            return false;
        }

        Like like = new Like(member, requestDto.getTargetId(), requestDto.getTargetType());
        likeRepository.save(like);
        return true;
    }

    @Transactional
    public boolean unLike(Member member, LikeRequestDto requestDto){
        Optional<Like> existing = likeRepository.findByMemberAndTargetIdAndTargetType(member, requestDto.getTargetId(), requestDto.getTargetType());

        if(existing.isPresent()){
            likeRepository.delete(existing.get());
            return true;
        }

        return false;
    }

    public List<LikerResponseDto> getLikers(Long boardId){
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()-> new RuntimeException("게시글을 찾을 수 없습니다."));

        List<Like> likes = likeRepository.findByTargetTypeAndTargetId(TargetType.BOARD,boardId);

        return likes.stream().map(like -> {Member member = like.getMember();
            return new LikerResponseDto(member.getId(), member.getName());
        }).collect(Collectors.toList());
    }

    private void validateTarget(LikeRequestDto requestDto){
        switch(requestDto.getTargetType()){
            case BOARD :
                boardRepository.findById(requestDto.getTargetId())
                        .orElseThrow(()-> new RuntimeException("존재하지 않는 게시글입니다."));
                break;
            case COMMENT:
                commentRepository.findById(requestDto.getTargetId())
                        .orElseThrow(()-> new RuntimeException("존재하지 않는 댓글입니다."));
                break;
            default:
                throw new RuntimeException("지원하지 않는 좋아요입니다.");
        }
    }
}
