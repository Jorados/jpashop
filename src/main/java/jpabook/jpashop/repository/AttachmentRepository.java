package jpabook.jpashop.repository;


import jpabook.jpashop.domain.UploadFIle2;
import org.springframework.data.jpa.repository.JpaRepository;

//스프링 데이터 jpa
public interface AttachmentRepository extends JpaRepository<UploadFIle2, Long> {
}
