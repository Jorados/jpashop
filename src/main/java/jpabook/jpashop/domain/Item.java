package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jpabook.jpashop.domain.embedded.UploadFile;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "dtype")
@Getter @Setter
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @JsonIgnore
    @OneToMany(mappedBy = "item",cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

//    @ManyToMany(mappedBy = "items")
//    private List<Category> categories = new ArrayList<>();

    private String author;
    private String isbn;
    private String itemText;
    private int likeCount2;
    @Column(columnDefinition = "integer default 0", nullable = false)
    private Long countVisit;

    @Embedded
    private UploadFile attachFile;

    @JsonIgnore
    @OneToMany(mappedBy = "item",cascade = CascadeType.ALL)
    private List<UploadFile2> imageFiles = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "item",cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "item",cascade = CascadeType.ALL)
    private List<Likes> likes = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //==비즈니스 로직==//
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

    public void addLikeCount2(){
        likeCount2 += 1;
    }

    public void minusLikeCount2(){
        likeCount2 -= 1;
    }

    public void updateVisit(Long countVisit){
        this.countVisit = countVisit;
    }


}
