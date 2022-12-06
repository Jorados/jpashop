package jpabook.jpashop.repository;


import jpabook.jpashop.domain.Board;
import jpabook.jpashop.domain.Likes;
import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class LikesRepository {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public Likes like(Likes likes){
        em.persist(likes);
        return likes;
    }

    @Transactional
    public void unlike(Long memberId, Long boardId, Board board){
        Likes findLikes = em.createQuery("select l from Likes l where l.member.id = :memberId and l.board.id = :boardId",Likes.class)
                .setParameter("memberId",memberId)
                .setParameter("boardId",boardId)
                .getSingleResult();

        board.getLikes().remove(findLikes);
        em.remove(findLikes);
    }

    public Likes findLike(Member member, Board board){
        Likes findLike;
        try {
            findLike = em.createQuery("select l from Likes l where l.member = :member and l.board = :board",Likes.class)
                    .setParameter("member",member)
                    .setParameter("board",board)
                    .getSingleResult();
        }
        catch(NoResultException e){
            return null;
        }
        return findLike;
    }
}
