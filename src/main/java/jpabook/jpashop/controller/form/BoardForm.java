package jpabook.jpashop.controller.form;


import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
public class BoardForm {


    private Long id;
    @NotEmpty(message = "제목은 필수입니다.")
    private String name;
    private String content;
    private LocalDateTime writeDate; //작성 시간
    private Long countVisit;

}
