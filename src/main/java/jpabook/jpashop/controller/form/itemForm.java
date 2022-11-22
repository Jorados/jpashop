package jpabook.jpashop.controller.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class itemForm {

    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    private MultipartFile attachFile;
    private List<MultipartFile> imageFiles;

    private String author;
    private String isbn;
    private String itemText;
}
