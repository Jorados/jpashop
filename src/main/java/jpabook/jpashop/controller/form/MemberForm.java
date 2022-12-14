package jpabook.jpashop.controller.form;

import jpabook.jpashop.domain.embedded.Address;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "아이디는 필수 입니다")
    private String loginId;
    @NotEmpty(message = "비밀번호는 필수 입니다")
    private String password;

    @NotEmpty(message = "회원 이름은 필수 입니다")
    private String name;

    private Address address;

    private String city;
    private String street;
    private String zipcode;


}
