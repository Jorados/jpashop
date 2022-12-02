package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class Comment {

    @Id @GeneratedValue
    @Column(name="comment_id")
    private Long id;

    private String content; //내용

    private LocalDateTime writeDate; //작성자

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name="userId")
    private Member member;

}
