package jpabook.jpashop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
public class UploadFile2 {

    @Id
    @GeneratedValue
    @Column(name = "UploadFile_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private String uploadFileName;
    private String storeFileName;


    public UploadFile2(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

    public UploadFile2() {
    }

    public void setItem(Item item) {
        this.item = item;
        item.getImageFiles().add(this);
    }
}
