package jpabook.jpashop.controller;


import jpabook.jpashop.controller.form.BoardForm;
import jpabook.jpashop.controller.form.itemForm;
import jpabook.jpashop.domain.Board;
import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.BoardSearch;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.BoardService;
import jpabook.jpashop.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.rule.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;

    @GetMapping("/boards")
    public String BoardList(Model model){
        List<Board> findBoards = boardService.findAll();
        List<Member> findMembers = memberService.findMembers();
        List<Board> findBoards2 = boardService.findBoardByMember();

        model.addAttribute("findMembers",findMembers);
        model.addAttribute("findBoards",findBoards);
        model.addAttribute("findBoards2",findBoards2);
        return "boards/boardList";
    }

    @GetMapping("/boards/new")
    public String createBoardForm(Model model){
        model.addAttribute("boardForm",new BoardForm());
        return "boards/createBoardForm";
    }

    @PostMapping("/boards/new")
    public String createBoard(BoardForm form, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.info("bindingResult.hasErrors 글 작성 에러 발생");
            return "boards/createBoardForm";
        }

        LocalDateTime now = LocalDateTime.now();
        Board board = new Board();
        board.setName(form.getName());
        board.setContent(form.getContent());
        board.setWriteDate(LocalDateTime.now());

        boardService.saveBoard(board);
        return "redirect:/boards";
    }

    @GetMapping("boards/{boardId}/edit")
    public String updateBoardForm(@PathVariable("boardId") Long boardId, Model model) {
        Board findBoard = boardService.findOne(boardId);
        BoardForm form = new BoardForm();
        form.setName(findBoard.getName());
        form.setContent(findBoard.getContent());

        model.addAttribute("form", form);
        return "boards/updateBoardForm";
    }

    @PostMapping("boards/{boardId}/edit")
    public String updateBoard(@PathVariable("boardId") Long boardId,BoardForm form, BindingResult bindingResult ){

        if(bindingResult.hasErrors()){
            log.info("binding error 발생");
        }

        boardService.updateBoard(boardId,form.getName(),form.getContent());
        return "redirect:/boards";
    }

    @GetMapping("boards/{boardId}/read")
    public String readBoard(@PathVariable("boardId") Long boardId,Model model){
        Board findBoard = boardService.findOne(boardId);

        BoardForm form = new BoardForm();
        form.setName(findBoard.getName());
        form.setContent(findBoard.getContent());

        model.addAttribute("form",form);
        return "boards/readBoardForm";
    }


}
