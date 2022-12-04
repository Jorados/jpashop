package jpabook.jpashop.controller;


import jpabook.jpashop.controller.form.BoardForm;
import jpabook.jpashop.controller.form.CommentForm;
import jpabook.jpashop.controller.form.itemForm;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.repository.BoardSearch;
import jpabook.jpashop.repository.CommentRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.BoardService;
import jpabook.jpashop.service.CommentService;
import jpabook.jpashop.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.rule.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
@Controller
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final CommentRepository commentRepository;
    @GetMapping("/boards")
    public String BoardList(Model model){

        List<Board> findBoards = boardService.findAll();
        model.addAttribute("findBoards",findBoards);
        return "boards/boardList";
    }


    @GetMapping("/boards/new")
    public String createBoardForm(@RequestParam("memberId") Long memberId,Model model){
        Member findMember = memberService.findOne(memberId);
        model.addAttribute("findMember",findMember);
        model.addAttribute("boardForm",new BoardForm());
        return "boards/createBoardForm";
    }

    @PostMapping("/boards/new")
    public String createBoard(@RequestParam("memberId") Long memberId, BoardForm form, BindingResult bindingResult){
        Member member = memberRepository.findOne(memberId);

        if(bindingResult.hasErrors()){
            log.info("bindingResult.hasErrors 글 작성 에러 발생");
            return "boards/createBoardForm";
        }


        LocalDateTime now = LocalDateTime.now();
        Board board = new Board();
        board.setId(form.getId());
        board.setMember(member);
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
    public String readBoard(@PathVariable("boardId") Long boardId, Model model){
        Board findBoard = boardService.findOne(boardId);
        List<Comment> comments = commentRepository.findCommentBoardId(boardId);


        //조회 누를 때 마다 조회수 증가
        //게시글을 작성한 memberId를 가지고 와서 거기에다가 조회수 +1
        Long countVisit = findBoard.getCountVisit() + 1L;
        BoardForm boardForm = new BoardForm();
        boardForm.setCountVisit(countVisit);
        boardService.updateVisit(findBoard.getId(),boardForm);

        model.addAttribute("comments",comments);
        model.addAttribute("findBoard",findBoard);
        return "boards/readBoardForm";
    }

    @PostMapping("boards/{boardId}/read")
    public String addComment(@PathVariable("boardId") Long boardId,@ModelAttribute CommentForm form, Model model, @SessionAttribute(name = "loginMember")Member loginMember){

        Board findBoard = boardService.findOne(boardId);
        Member findMember = memberService.findOne(loginMember.getId());

        Comment comment = new Comment();
        comment.setName(findMember.getName());
        comment.setContent(form.getContent());
        comment.setWriteDate(LocalDateTime.now());
        comment.setMember(findMember);
        comment.setBoard(findBoard);

        commentRepository.save(comment);
        List<Comment> comments = commentRepository.findCommentBoardId(boardId);
        model.addAttribute("comments",comments);
        model.addAttribute("findBoard",findBoard);

        return "/boards/readBoardForm";
    }


}