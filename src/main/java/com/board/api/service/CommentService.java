package com.board.api.service;

import com.board.api.dto.comment.CommentResponseDto;
import com.board.api.dto.comment.CreateCommentRequestDto;
import com.board.api.dto.comment.UpdateCommentRequestDto;
import com.board.api.entity.Board;
import com.board.api.entity.Comment;
import com.board.api.entity.Member;
import com.board.api.repository.BoardRepository;
import com.board.api.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public CommentResponseDto create(Member member, Long boardId, CreateCommentRequestDto createCommentRequestDto){
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("존재하지 않는 게시글입니다."));


        Comment comment = createCommentRequestDto.toEntity(member, board);
        Comment newComment = commentRepository.save(comment);
        CommentResponseDto commentResponseDto = CommentResponseDto.from(newComment);
        return commentResponseDto;

    }

    @Transactional
    public CommentResponseDto update(Member member, Long commentId, UpdateCommentRequestDto updateCommentRequestDto){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("존재하지 않는 댓글입니다."));

        if(!member.getId().equals(comment.getMember().getId())) {
            throw new RuntimeException("접근할수없는 댓글입니다.");
        }

        comment.update(updateCommentRequestDto);

        CommentResponseDto commentResponseDto = CommentResponseDto.from(comment);
        return commentResponseDto;
    }

    public Page<CommentResponseDto> findAll(){
    }

    public Long delete(Long id){
    }
}

