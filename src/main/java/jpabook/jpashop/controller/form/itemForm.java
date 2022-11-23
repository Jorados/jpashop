package jpabook.jpashop.controller.form;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class itemForm {

    private Long id;

    @NotEmpty(message = "책이름은 필수 입니다")
    private String name;

    private int price;
    private int stockQuantity;

    private MultipartFile attachFile;
    private List<MultipartFile> imageFiles;

    private String author;
    private String isbn;
    private String itemText;
}
