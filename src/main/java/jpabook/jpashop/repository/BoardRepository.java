package jpabook.jpashop.repository;


import jpabook.jpashop.domain.Board;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityManager em;

    public void save(Board board){
        em.persist(board);
    }

    public Board findOne(Long id) {
        return em.find(Board.class, id);
    }

    public void delete(Long id){
        Board findBoard = em.find(Board.class, id);
        em.remove(findBoard);
    }

    public List<Board> findAll(){
        return em.createQuery("select b from Board b", Board.class)
                .getResultList();
    }

    //member id 글 조회 - 내가 쓴 글 찾기
    public List<Board> findByMemberId(Long memberId){
        return em.createQuery("select b from Board b where b.member.id = : memberId" , Board.class)
                .setParameter("memberId",memberId)
                .getResultList();
    }

    //member name 글 조회
    public List<Board> findByName(String name){
        return em.createQuery("select b from Board b where b.member.name = :name",Board.class)
                .setParameter("name",name)
                .getResultList();
    }



}
