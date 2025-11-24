package com.board.api.service;

import com.board.api.dto.comment.CommentDeleteResponseDto;
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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public CommentResponseDto create(Member member, CreateCommentRequestDto requestDto){
        Board board = boardRepository.findById(requestDto.getBoardId()).orElseThrow(() -> new RuntimeException("존재하지 않는 게시글입니다."));

        Comment parentComment = null;
        if(requestDto.getParentId() != null){
            parentComment = commentRepository.findById(requestDto.getParentId()).orElseThrow(() -> new RuntimeException("부모 댓글이 존재하지 않습니다."));
        }

        Comment comment = requestDto.toEntity(member, board, parentComment);
        Comment newComment = commentRepository.save(comment);
        return CommentResponseDto.from(newComment);

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

    public Page<CommentResponseDto> findAll(Long boardId, Pageable pageable){
        Page<Comment> comments = commentRepository.findAllByBoardIdAndParentIsNull(boardId,pageable);

        return comments.map(CommentResponseDto::from);
    }

    @Transactional
    public CommentDeleteResponseDto delete(Long id){
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        // 자식 댓글이 있는 경우 -> soft delete
        if(!comment.getChildren().isEmpty()){
            comment.setDeleted(true);
            comment.setContent("삭제된 댓글입니다.");

            // 작성자 정보를 지우고 싶다면(선택)
//            comment.setMember(null);
            return CommentDeleteResponseDto.soft(comment.getId());
        }

        commentRepository.delete(comment);
        return CommentDeleteResponseDto.hard(id);
    }

    public Page<CommentResponseDto> findMyComments(Member member, Pageable pageable){
        Page<Comment> commentPage = commentRepository.findByMemberAndDeletedFalse(member, pageable);
        return commentPage.map(CommentResponseDto::from);
    }
}

