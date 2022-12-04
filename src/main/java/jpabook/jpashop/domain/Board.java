package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;


@Entity
@Setter
@Getter
public class Board {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String name;
    private String content;
    private LocalDateTime writeDate;
    private int likeCount;
    @Column(columnDefinition = "integer default 0", nullable = false)
    private Long countVisit;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @JsonIgnore
    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "board",cascade = CascadeType.ALL)
    private List<Likes> likes = new ArrayList<>();



    //연관관계 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getBoards().add(this);
    }

    //비즈니스 로직
    public void addLikeCount(){
        likeCount += 1;
    }

    public void minusLikeCount(){
        likeCount -= 1;
    }

    public void updateVisit(Long countVisit){
        this.countVisit = countVisit;
    }
}


