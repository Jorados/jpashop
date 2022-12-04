package jpabook.jpashop.service;

import com.fasterxml.jackson.annotation.OptBoolean;
import jpabook.jpashop.controller.form.BoardForm;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.enumFile.DeliveryStatus;
import jpabook.jpashop.repository.BoardRepository;
import jpabook.jpashop.repository.BoardSearch;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void saveBoard(Board board){
        boardRepository.save(board);
    }

    @Transactional
    public void deleteBoard(Long id){
        boardRepository.delete(id);
    }

    public List<Board> findAll(){
        List<Board> findBoards = boardRepository.findAll();
        return findBoards;
    }

    public Board findOne(Long boardId){
        Board findBoard = boardRepository.findOne(boardId);
        return findBoard;
    }

    public List<Board> findBoardByMember(){
        List<Board> findBoard = boardRepository.findBoardByMember();
        return findBoard;
    }

    public List<Board> findByMemberId(Long memberId){
        List<Board> findBoard = boardRepository.findByMemberId(memberId);
        return findBoard;
    }

    @Transactional
    public void updateBoard(Long boardId, String name, String content) {
        Board board = boardRepository.findOne(boardId);
        board.setName(name);
        board.setContent(content);
    }

    @Transactional
    public void updateVisit(Long boardId, BoardForm boardForm){
        Board findBoard = boardRepository.findOne(boardId);
        findBoard.updateVisit(boardForm.getCountVisit());
    }


}
