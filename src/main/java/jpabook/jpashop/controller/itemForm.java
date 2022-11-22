package jpabook.jpashop.controller;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class itemForm {

    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    private MultipartFile attachFile;
    private List<MultipartFile> imageFiles;
    private List<MultipartFile> generalFiles;

    private Map<AttachmentType, List<MultipartFile>> attachmentFiles = new ConcurrentHashMap<>();

    private String author;
    private String isbn;

    @Builder
    public itemForm(Long id, String name, int price, List<MultipartFile> imageFiles, List<MultipartFile> generalFiles) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageFiles = (imageFiles != null) ? imageFiles : new ArrayList<>();
        this.generalFiles = (generalFiles != null) ? generalFiles : new ArrayList<>();
    }

    private Map<AttachmentType, List<MultipartFile>> getAttachmentTypeListMap() {
        Map<AttachmentType, List<MultipartFile>> attachments = new ConcurrentHashMap<>();
        attachments.put(AttachmentType.IMAGE, imageFiles);
        attachments.put(AttachmentType.GENERAL, generalFiles);
        return attachments;
    }
}
