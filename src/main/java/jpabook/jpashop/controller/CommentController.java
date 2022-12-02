package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Comment;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.CommentService;
import jpabook.jpashop.session.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
public class CommentController {


    private final CommentService commentService;

    @PostMapping("/comment_write")
    public String replyWrite(@ModelAttribute Comment comment, Long boardId, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)Member member){
        return commentService.commentWrite(comment, member, boardId);
    }


    @PostMapping("/reply_delete")
    public String replyDelete(@ModelAttribute Comment comment, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
    Member member){
        return commentService.commentDelete(comment);
    }
}
