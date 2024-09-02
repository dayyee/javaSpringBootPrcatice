package com.example.shop.item;

// 이거저거 검사하거나 DB 입출력하거나 => 비즈니스 로직 : 이걸 담아두는 클래스는 Service

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository; // + lombok 문법

    public void saveItem(String title, Integer price) throws Exception {
        try{
            if(title != null && price >= 0){
                Item item = new Item();
                item.setTitle(title);
                item.setPrice(price);
                itemRepository.save(item);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            throw new Exception();
        }
    }

    public void updateItem(Long id, String title, Integer price) throws Exception {
        Optional<Item> result = findItemById(id);
    // jpa는 item.id = id 로, 수정 기능 가능. id값이 같으면 덮어써준다.
            try{
                if(result.isPresent()){
                    if(title != null && title.length() < 100 && price >= 0){
//                        Item resultItem = result.get();
//                        System.out.println("result Item" + resultItem); // result ItemItem(id=4, title=치마, price=12000)
                        Item item = new Item();
                        item.setId(id); // 이미 있는 Id면 덮어씌워줌.
                        item.setTitle(title);
                        item.setPrice(price);
                        itemRepository.save(item);
                    }
                }
            }catch(Exception e){
                System.out.println(e.getMessage());
                throw new Exception("Item not found");
            }


    }

    public Optional<Item> findItemById(Long id){
        return itemRepository.findById(id);
    }

    public List<Item> findAllItems(){
        return itemRepository.findAll();
    }

    // 삭제 service
    public void deleteItemById(Long id){
        if(itemRepository.existsById(id)){
            itemRepository.deleteById(id);
        }
        else {
            throw new IllegalArgumentException("Item with Id " + id + " does not exist.");
        }
    }

}
