package org.example.board.service;

import org.example.board.dto.BoardDTO;
import org.example.board.entity.Board;
import org.example.board.entity.Member;

public interface BoardService {
    // 새글을 등록하는 기능
    Long register(BoardDTO dto);
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
