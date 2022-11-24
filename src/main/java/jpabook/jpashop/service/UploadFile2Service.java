package jpabook.jpashop.service;

import jpabook.jpashop.domain.UploadFile2;
import jpabook.jpashop.file.FileStore;
import jpabook.jpashop.repository.UploadFile2Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UploadFile2Service {

    private final UploadFile2Repository uploadFile2Repository;
    private final FileStore fileStore;

    public List<UploadFile2> findImageFiles() {
        List<UploadFile2> imageFiles = uploadFile2Repository.findAll();
        return imageFiles;
    }

}
