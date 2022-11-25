package jpabook.jpashop.service;

import jpabook.jpashop.domain.Board;
import jpabook.jpashop.domain.Item;
import jpabook.jpashop.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

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

    public List<Board> findBoardByName(String memberName){
        List<Board> findBoard = boardRepository.findByName(memberName);
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

}
