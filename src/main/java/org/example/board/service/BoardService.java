package org.example.board.service;

import org.example.board.dto.BoardDTO;
import org.example.board.dto.PageRequestDTO;
import org.example.board.dto.PageResultDTO;
import org.example.board.entity.Board;
import org.example.board.entity.Member;

public interface BoardService {
    // 새글을 등록하는 기능
    Long register(BoardDTO dto);
    //게시목록 처리 기능

    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);
    // Entity를 DTO로 변환하는 메소드
    default BoardDTO entityToDTO(Board board, Member member, Long replyCount){
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue())
                .build();

        return boardDTO;
    }
    // DTO를 Entity로 변환하는 메소드
    default Board dtoToEntity(BoardDTO dto){
        Member member = Member.builder()
                .email(dto.getWriterEmail())
                .build();

        Board board=Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();
        return board;
    }
}
