package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Attachment {

    @Id
    @GeneratedValue
    @Column(name = "UploadFile_id")
    private Long id;
    private String uploadFileName;
    private String storeFileName;

    @Enumerated(EnumType.STRING)
    private AttachmentType attachmentType;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;


    public Attachment(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

}
