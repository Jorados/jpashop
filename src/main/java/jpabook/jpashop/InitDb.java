package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.embedded.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Member member = createMember("뱅갈호랑이", "서울", "1", "1111","test1","1234");
            em.persist(member);

            Item book1 = createBook("JPA1 BOOK", 10000, 100,0L);
            em.persist(book1);

            Item book2 = createBook("JPA2 BOOK", 20000, 100,0L);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);

            Board board = createBoard(member,"제목1", "첫 번째 게시글 내용",0L);
            em.persist(board);
        }

        public void dbInit2() {
            Member member = createMember("조성진", "진주", "2", "2222","test2","1234");
            em.persist(member);

            Item book1 = createBook("SPRING1 BOOK", 20000, 200,0L);
            em.persist(book1);

            Item book2 = createBook("SPRING2 BOOK", 40000, 300,0L);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);

            Board board = createBoard(member,"제목2", "두 번째 게시글 내용",0L);
            em.persist(board);
        }

        private Member createMember(String name, String city, String street, String zipcode,String loginId, String password) {
            Member member = new Member();
            member.setName(name);
            member.setLoginId(loginId);
            member.setPassword(password);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }

        private Item createBook(String name, int price, int stockQuantity,Long countVisit) {
            Item book1 = new Item();
            book1.setName(name);
            book1.setPrice(price);
            book1.setStockQuantity(stockQuantity);
            book1.setCountVisit(0L);
            return book1;
        }

        private Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        private Board createBoard(Member member,String name, String content,Long countVisit){
            Board board = new Board();
            board.setMember(member);
            board.setName(name);
            board.setContent(content);
            board.setWriteDate(LocalDateTime.now());
            board.setCountVisit(countVisit);
            return board;
        }
    }
}

