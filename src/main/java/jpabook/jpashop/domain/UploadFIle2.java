package jpabook.jpashop.domain;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class UploadFIle2 {

    @Id
    @GeneratedValue
    @Column(name = "UploadFile_id")
    private Long id;
    private String uploadFileName;
    private String storeFileName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public UploadFIle2(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
