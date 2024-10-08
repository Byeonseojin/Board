package org.example.board.controller;

import lombok.RequiredArgsConstructor;
import org.example.board.dto.BoardDTO;
import org.example.board.dto.PageRequestDTO;
import org.example.board.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/board/")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        model.addAttribute("result", boardService.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register(){

    }
    @PostMapping("/register")
    public String registerPost(BoardDTO dto, RedirectAttributes redirectAttributes){
        Long bno = boardService.register(dto);
        redirectAttributes.addFlashAttribute("msg", bno);
        return "redirect:/board/list";
    }
    @GetMapping({"/read", "/modify"})
    public void read(Long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO,Long bno, Model model){
        BoardDTO boardDTO = boardService.get(bno);
        model.addAttribute("dto", boardDTO);
    }

    @PostMapping("/remove")
    public String remove(long bno, RedirectAttributes redirectAttributes){
        boardService.removeWithReplies(bno);
        redirectAttributes.addFlashAttribute("msg", bno);

        return "redirect:/board/list";
    }

    @PostMapping("/modify")
    public String modify(BoardDTO dto, @ModelAttribute("requestDTO")PageRequestDTO requestDTO, RedirectAttributes redirectAttributes){
        boardService.modify(dto);
        redirectAttributes.addAttribute("page", requestDTO.getPage());
        redirectAttributes.addAttribute("bno", dto.getBno()); //addFlashAttribute사용하면안됨
        redirectAttributes.addAttribute("type",requestDTO.getType()); //addFlashAttribute사용하면안됨
        redirectAttributes.addAttribute("keyword", requestDTO.getKeyword()); //addFlashAttribute사용하면안됨


        return "redirect:/board/read";
    }
}
