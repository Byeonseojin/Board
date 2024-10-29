package org.example.board.service;

import org.example.board.dto.ReplyDTO;
import org.example.board.entity.Board;
import org.example.board.entity.Reply;

import java.util.List;

public interface ReplyService {
    // 댓글 등록 기능
    Long register(ReplyDTO replyDTO);
    // 댓글 수정기능
    void modify(ReplyDTO replyDTO);
    // 댓글 삭제 기능
    void remove(Long rno);
    // 댓글 목록 반환 가능
    List<ReplyDTO> getList(Long bno);

    // ReplyDTO => Reply(Entity) 변환
    default Reply dtoToEntity(ReplyDTO dto){
        Board board = Board.builder()
                .bno(dto.getBno())
                .build();

        Reply reply = Reply.builder()
                .rno(dto.getRno())
                .text(dto.getText())
                .replyer(dto.getReplyer())
                .board(board)
                .build();

        return reply;
    }
    default ReplyDTO entityToDTO(Reply reply){
        ReplyDTO replyDTO = ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replyer(reply.getReplyer())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate()).build();
        return replyDTO;
    }
}