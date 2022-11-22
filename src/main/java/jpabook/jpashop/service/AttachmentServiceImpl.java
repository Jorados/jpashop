package jpabook.jpashop.service;

import jpabook.jpashop.domain.UploadFIle2;
import jpabook.jpashop.file.FileStore;
import jpabook.jpashop.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService{

    private final AttachmentRepository attachmentRepository;
    private final FileStore fileStore;

    public List<UploadFIle2> findAttachments() {
        List<UploadFIle2> attachments = attachmentRepository.findAll();
        return attachments;
    }
}
