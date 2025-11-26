package com.board.api.service;

import com.board.api.dto.comment.CommentDeleteResponseDto;
import com.board.api.dto.comment.CommentResponseDto;
import com.board.api.dto.comment.CreateCommentRequestDto;
import com.board.api.dto.comment.UpdateCommentRequestDto;
import com.board.api.dto.member.MemberResponseDto;
import com.board.api.entity.Board;
import com.board.api.entity.Comment;
import com.board.api.entity.Member;
import com.board.api.enums.TargetType;
import com.board.api.repository.BoardRepository;
import com.board.api.repository.CommentRepository;
import com.board.api.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public CommentResponseDto create(Member member, CreateCommentRequestDto requestDto){
        Board board = boardRepository.findById(requestDto.getBoardId()).orElseThrow(() -> new RuntimeException("존재하지 않는 게시글입니다."));

        Comment parentComment = null;
        if(requestDto.getParentId() != null){
            parentComment = commentRepository.findById(requestDto.getParentId()).orElseThrow(() -> new RuntimeException("부모 댓글이 존재하지 않습니다."));
        }

        Comment comment = requestDto.toEntity(member, board, parentComment);
        Comment newComment = commentRepository.save(comment);

         return CommentResponseDto.builder()
                .id(newComment.getId())
                .member(MemberResponseDto.from(newComment.getMember()))
                .deleted(newComment.isDeleted())
                .content(newComment.getContent())
                .children(new ArrayList<>())
                .likeCount(0L)
                .liked(false)
                .createdDate(newComment.getCreatedDate())
                .modifiedDate(newComment.getModifiedDate())
                .build();

    }

    @Transactional
    public CommentResponseDto update(Member member, Long commentId, UpdateCommentRequestDto updateCommentRequestDto){
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("존재하지 않는 댓글입니다."));

        if(!member.getId().equals(comment.getMember().getId())) {
            throw new RuntimeException("접근할수없는 댓글입니다.");
        }

        comment.update(updateCommentRequestDto);


        Long likeCount = likeRepository.countByTargetTypeAndTargetId(TargetType.COMMENT, comment.getId());
        boolean liked = likeRepository.existsByMemberAndTargetTypeAndTargetId(member, TargetType.COMMENT, comment.getId());

        return CommentResponseDto.builder()
                .id(comment.getId())
                .member(MemberResponseDto.from(comment.getMember()))
                .deleted(comment.isDeleted())
                .content(comment.getContent())
                .children(new ArrayList<>())
                .likeCount(likeCount)
                .liked(liked)
                .createdDate(comment.getCreatedDate())
                .modifiedDate(comment.getModifiedDate())
                .build();
    }

    public Page<CommentResponseDto> findAll(Long boardId, Member member, Pageable pageable){
        Page<Comment> comments = commentRepository.findAllByBoardIdAndParentIsNull(boardId,pageable);

        return comments.map(comment -> {
            Long likeCount = likeRepository.countByTargetTypeAndTargetId(TargetType.COMMENT, comment.getId());
            boolean liked = likeRepository.existsByMemberAndTargetTypeAndTargetId(member, TargetType.COMMENT, comment.getId());
            List<CommentResponseDto> children = buildCommentDtos(comment.getChildren(), member);

            return CommentResponseDto.builder()
                    .id(comment.getId())
                    .member(MemberResponseDto.from(comment.getMember()))
                    .deleted(comment.isDeleted())
                    .content(comment.getContent())
                    .children(children)
                    .likeCount(likeCount)
                    .liked(liked)
                    .createdDate(comment.getCreatedDate())
                    .modifiedDate(comment.getModifiedDate())
                    .build();
        });
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
        return commentPage.map(comment -> {
            Long likeCount = likeRepository.countByTargetTypeAndTargetId(TargetType.COMMENT, comment.getId());
            boolean liked = likeRepository.existsByMemberAndTargetTypeAndTargetId(member, TargetType.COMMENT, comment.getId());

            return CommentResponseDto.builder()
                    .id(comment.getId())
                    .member(MemberResponseDto.from(comment.getMember()))
                    .deleted(comment.isDeleted())
                    .content(comment.getContent())
                    .children(new ArrayList<>())
                    .likeCount(likeCount)
                    .liked(liked)
                    .createdDate(comment.getCreatedDate())
                    .modifiedDate(comment.getModifiedDate())
                    .build();
        });
    }

    public List<CommentResponseDto> buildCommentDtos(List<Comment> comments, Member member) {
        if (comments == null || comments.isEmpty()) {
            return new ArrayList<>(); // children이 없으면 빈 리스트 반환
        }

        return comments.stream().map(comment -> {
            Long likeCount = likeRepository.countByTargetTypeAndTargetId(TargetType.COMMENT, comment.getId());
            boolean liked = likeRepository.existsByMemberAndTargetTypeAndTargetId(member, TargetType.COMMENT, comment.getId());

            List<CommentResponseDto> children = buildCommentDtos(comment.getChildren(), member);

            return CommentResponseDto.builder()
                    .id(comment.getId())
                    .member(MemberResponseDto.from(comment.getMember()))
                    .deleted(comment.isDeleted())
                    .content(comment.getContent())
                    .children(children) // children이 없으면 빈 리스트
                    .likeCount(likeCount)
                    .liked(liked)
                    .createdDate(comment.getCreatedDate())
                    .modifiedDate(comment.getModifiedDate())
                    .build();
        }).collect(Collectors.toList());
    }
}

