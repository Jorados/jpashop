package jpabook.jpashop.service;

import jpabook.jpashop.domain.Board;
import jpabook.jpashop.domain.Comment;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberService memberService;
    private final BoardService boardService;

    public String commentWrite(Comment comment, Member member, Long boardId){
        Member findMember = memberService.findOne(member.getId());
        Optional<Board> findBoard = Optional.ofNullable(boardService.findOne(boardId));

        comment.setBoard(findBoard.get());
        comment.setMember(findMember);
        commentRepository.save(comment);
        return "home";
    }

    public String commentDelete(Comment comment){
        commentRepository.delete(comment.getId());
        return "home";
    }
}
