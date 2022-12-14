package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Board;
import jpabook.jpashop.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

    //이미지 파일  찾기
    //패치 조인 ->>>>> 지연 로딩 x 다 조회
    public List<Item> findImageFiles(String storeImageFiles){
        return em.createQuery("select i from Item i join fetch i.UploadFile2 =:storeImageFiles",Item.class)
                .setParameter("storeImageFiles",storeImageFiles)
                .getResultList();
    }

    //이너조인
    public List<Item> findImageFiles2(String storeFileName){
        return em.createQuery("select i from Item i inner join i.UploadFile2 u WHERE u.storeFileName =:storeFileName",Item.class)
                .setParameter("storeFileName", storeFileName)
                .getResultList();
    }

    //내가 좋아요 누른 글 조회
    public List<Item> findLikesItem(Long memberId){
        return em.createQuery("select l.item from Likes l where l.member.id = :memberId",Item.class)
                .setParameter("memberId",memberId)
                .getResultList();
    }
}
