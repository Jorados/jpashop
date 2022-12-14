package jpabook.jpashop.service;

import jpabook.jpashop.controller.form.BoardForm;
import jpabook.jpashop.controller.form.ItemForm;
import jpabook.jpashop.domain.Board;
import jpabook.jpashop.domain.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item item = itemRepository.findOne(itemId);
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
    }

    @Transactional
    public void updateVisit(Long itemId, ItemForm itemForm){
        Item findItem = itemRepository.findOne(itemId);
        findItem.updateVisit(itemForm.getCountVisit());
    }


    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    public  List<Item> findUploadFile(String storeImageFiles){
        return itemRepository.findImageFiles(storeImageFiles);
    }

}
