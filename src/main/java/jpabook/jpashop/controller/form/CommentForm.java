package jpabook.jpashop.controller.form;

import jpabook.jpashop.domain.Board;
import jpabook.jpashop.domain.Member;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class CommentForm {

    private Long id;
    private String name; //댓글제목
    private String writer; //작성자
    private String content; //내용
    private LocalDateTime writeDate; //작성일


}
