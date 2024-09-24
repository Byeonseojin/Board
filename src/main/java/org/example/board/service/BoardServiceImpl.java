package org.example.board.service;

import lombok.RequiredArgsConstructor;
import org.example.board.dto.BoardDTO;
import org.example.board.dto.PageRequestDTO;
import org.example.board.dto.PageResultDTO;
import org.example.board.entity.Board;
import org.example.board.entity.Member;
import org.example.board.repository.BoardRepository;
import org.example.board.repository.ReplyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    private final BoardRepository repository;
    private final ReplyRepository replyRepository;
    @Override
    public Long register(BoardDTO dto) {
        Board board = dtoToEntity(dto);
        repository.save(board);

        return board.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {

        Function<Object[], BoardDTO> fn = (en -> entityToDTO((Board) en[0], (Member) en[1], (Long) en[2]));
        Page<Object[]> result = repository.getBoardWithReplyCount(pageRequestDTO.getPageable(Sort.by("bno").descending()));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public BoardDTO get(Long bno) {
        Object result = repository.getBoardByBno(bno);

        Object[] arr = (Object[]) result;

        BoardDTO boardDTO = entityToDTO((Board) arr[0],(Member) arr[1],(Long) arr[2]);
        return boardDTO;
    }
    @Transactional
    @Override
    public void removeWithReplies(Long bno) {
        //댓글 삭제
        replyRepository.deleteByBno(bno);
        //원글 삭제
        repository.deleteById(bno);
    }
    @Transactional
    @Override
    public void modify(BoardDTO boardDTO) {
        Board board = repository.getReferenceById(boardDTO.getBno());
        board.changetitle(board.getTitle());
        board.changeContent(board.getContent());

        repository.save(board);
    }
}
