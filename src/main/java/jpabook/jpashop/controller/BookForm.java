package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter @Setter
public class BookForm {

    private Long id;
    private String name;

    private MultipartFile attachFile;
    private List<MultipartFile> imageFiles;

    private int price;
    private int stockQuantity;

    private String author;
    private String isbn;
}
