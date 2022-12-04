package jpabook.jpashop.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Likes {

    @Id @GeneratedValue
    @Column(name = "likes_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public static Likes createLike(Member member, Board board) {
        Likes likes = new Likes();
        likes.setMember(member);
        likes.setBoard(board);

        return likes;
    }

    public void setMember(Member member) {
        this.member = member;
        member.getLikes().add(this);
    }
}
