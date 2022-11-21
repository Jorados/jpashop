package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Embeddable
@Getter @Setter
@AllArgsConstructor
public class UploadFile {

    private String uploadFileName;
    private String storeFileName;


}
