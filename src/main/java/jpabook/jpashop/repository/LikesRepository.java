package jpabook.jpashop.repository;


import jpabook.jpashop.domain.Board;
import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.Likes;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class LikesRepository {

    @PersistenceContext
    final EntityManager em;

    public Likes like(Likes likes){
        em.persist(likes);
        return likes;
    }

    public Likes like2(Likes likes){
        em.persist(likes);
        return likes;
    }

    public void unlike(Long memberId, Long boardId, Board board){
        Likes findLikes = em.createQuery("select l from Likes l where l.member.id = :memberId and l.board.id = :boardId",Likes.class)
                .setParameter("memberId",memberId)
                .setParameter("boardId",boardId)
                .getSingleResult();

        board.getLikes().remove(findLikes);
        em.remove(findLikes);
    }

    public void unlike2(Long memberId, Long itemId, Item item){
        Likes findLikes = em.createQuery("select l from Likes l where l.member.id = :memberId and l.item.id = :itemId",Likes.class)
                .setParameter("memberId",memberId)
                .setParameter("itemId",itemId)
                .getSingleResult();

        item.getLikes().remove(findLikes);
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

    public Likes findLike2(Member member, Item item){
        Likes findLike;
        try {
            findLike = em.createQuery("select l from Likes l where l.member = :member and l.item = :item",Likes.class)
                    .setParameter("member",member)
                    .setParameter("item",item)
                    .getSingleResult();
        }
        catch(NoResultException e){
            return null;
        }
        return findLike;
    }
}
