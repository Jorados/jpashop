package jpabook.jpashop.repository;


import jpabook.jpashop.domain.Board;
import jpabook.jpashop.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    //board.id에 해당하는 댓글만 출력할 수 있는 쿼리
    @Query("select c from Comment c where c.board.id = :id")
    List<Comment> findCommentBoardId(@Param("id") Long id);

    @Query("select c from Comment c where c.item.id = :id")
    List<Comment> findCommentItemId(@Param("id") Long id);

}
