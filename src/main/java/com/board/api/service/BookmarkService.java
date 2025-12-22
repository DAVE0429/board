package com.board.api.service;

import com.board.api.dto.board.BoardResponseDto;
import com.board.api.entity.Board;
import com.board.api.entity.Bookmark;
import com.board.api.entity.Member;
import com.board.api.enums.TargetType;
import com.board.api.repository.BoardRepository;
import com.board.api.repository.BookmarkRepository;
import com.board.api.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final BoardRepository boardRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public boolean addBookmark(Member member, Long boardId){

        if(member == null){
            throw new RuntimeException("로그인이 필요합니다.");
        }

        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("해당 게시글이 없습니다."));

        if(bookmarkRepository.existsByMemberAndBoard(member,board)){
            return false;
        }

        Bookmark bookmark = new Bookmark(member, board);
        bookmarkRepository.save(bookmark);
        return true;
    }

    @Transactional
    public boolean removeBookmark(Member member, Long boardId){

        if(member == null){
            throw new RuntimeException("로그인이 필요합니다.");
        }
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException("해당 게시글이 없습니다."));
        Bookmark bookmark = bookmarkRepository.findByMemberAndBoard(member,board).orElseThrow(()-> new RuntimeException("북마크가 존재하지 않습니다."));
        bookmarkRepository.delete(bookmark);
        return true;
    }

    public boolean isBookmarked(Member member,Long boardId){
        Board board = boardRepository.getReferenceById(boardId);
        return bookmarkRepository.existsByMemberAndBoard(member,board);
    }

    public long getBookmarkCount(Long boardId){
        Board board = boardRepository.getReferenceById(boardId);
        return bookmarkRepository.countByBoard(board);
    }

    public Page<BoardResponseDto> getMyBookmarks(Member member, Pageable pageable){
        Page<Board> boardPage = bookmarkRepository.findBookmarkedBoardsByMember(member, pageable);
        return boardPage.map(board -> {
            boolean liked = likeRepository.existsByMemberAndTargetTypeAndTargetId(member, TargetType.BOARD, board.getId());
            Long likeCount = likeRepository.countByTargetTypeAndTargetId(TargetType.BOARD,board.getId());
            BoardResponseDto boardResponseDto = BoardResponseDto.from(board, likeCount, liked);
            return boardResponseDto;
        });
    }
}
