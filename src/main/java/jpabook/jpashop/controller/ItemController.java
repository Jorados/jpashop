package jpabook.jpashop.controller;

import jpabook.jpashop.controller.form.BoardForm;
import jpabook.jpashop.controller.form.CommentForm;
import jpabook.jpashop.controller.form.ItemForm;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.embedded.UploadFile;
import jpabook.jpashop.file.FileStore;
import jpabook.jpashop.repository.CommentRepository;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.UploadFile2Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import javax.validation.Valid;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final FileStore fileStore;
    private final UploadFile2Service uploadFile2Service;
    private final CommentRepository commentRepository;
    private final MemberService memberService;
    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new ItemForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(@Valid ItemForm form, RedirectAttributes redirectAttributes, Model model, BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            return "items/createItemForm";
        }

        UploadFile attachFile = fileStore.storeFile(form.getAttachFile());
        List<UploadFile2> storeImageFiles = fileStore.storeFiles2(form.getImageFiles());

        Item bookItem = new Item();
        bookItem.setName(form.getName());
        bookItem.setPrice(form.getPrice());
        bookItem.setStockQuantity(form.getStockQuantity());
        bookItem.setAuthor(form.getAuthor());
        bookItem.setIsbn(form.getIsbn());
        bookItem.setItemText(form.getItemText());
        bookItem.setAttachFile(attachFile);
        bookItem.setImageFiles(storeImageFiles);
        bookItem.setCountVisit(0L);
        itemService.saveItem(bookItem);

        return "redirect:/items";
    }

//    @GetMapping("/items/{itemId}")
//    public String items(@PathVariable Long itemId, Model model) {
//        Item item = itemService.findOne(itemId);
//        model.addAttribute("item", item);
//        return "items/item-view";
//    }

    //업로드 파일 ResponseBody 에 담기
    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(filename));
    }

    //업로드 파일 다운로드
    //다운
    @GetMapping("/attach/{itemId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long itemId) throws MalformedURLException {
        Item item = itemService.findOne(itemId);
        String storeFileName = item.getAttachFile().getStoreFileName();
        String uploadFileName = item.getAttachFile().getUploadFileName();

        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));

        log.info("uploadFileName={}", uploadFileName);

        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }


    //상품목록
    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Item item = (Item) itemService.findOne(itemId);

        ItemForm form = new ItemForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());
        form.setItemText(item.getItemText());

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @GetMapping("items/{itemId}/readItem")
    public String readItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Item findItem = itemService.findOne(itemId);
        List<UploadFile2> imageFiles = uploadFile2Service.findImageFiles();
        List<Comment> comments = commentRepository.findCommentBoardId(itemId);

        Long countVisit = findItem.getCountVisit() + 1L;
        ItemForm itemForm = new ItemForm();
        itemForm.setCountVisit(countVisit);
        itemService.updateVisit(findItem.getId(),itemForm);

        model.addAttribute("findItem", findItem);
        model.addAttribute("imageFiles",imageFiles);
        model.addAttribute("comments",comments);
        return "items/readItemForm";
    }

    @PostMapping("items/{itemId}/readItem")
    public String addComment(@PathVariable("itemId") Long itemId, @ModelAttribute CommentForm form, Model model, @SessionAttribute(name = "loginMember") Member loginMember){
        Item findItem = itemService.findOne(itemId);
        Member findMember = memberService.findOne(loginMember.getId());

        Comment comment = new Comment();
        comment.setName(findMember.getName());
        comment.setContent(form.getContent());
        comment.setWriteDate(LocalDateTime.now());
        comment.setMember(findMember);
        comment.setItem(findItem);

        commentRepository.save(comment);
        List<Comment> comments = commentRepository.findCommentItemId(itemId);
        model.addAttribute("comments",comments);
        model.addAttribute("findItem",findItem);
        return "items/readItemForm";

    }
    @PostMapping("items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") ItemForm form) {
        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());
        return "redirect:/items";
    }
}





