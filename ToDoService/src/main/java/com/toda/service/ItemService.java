package com.toda.service;

import com.toda.Exception.ResourceNotFoundException;
import com.toda.dao.ItemDetailsRepository;
import com.toda.dao.ItemRepository;
import com.toda.model.Item;
import com.toda.model.Item_Details;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


import java.util.Optional;

@Service
public class ItemService {

    @Autowired
   private RestTemplate restTemplate;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemDetailsRepository itemDetailsRepository;


    // get item by id
    public Item findItemById(int id){
      return   itemRepository.findItemById(id);
    }

    @Transactional
    public Item addItem(Item item) {


        return itemRepository.save(item );
    }

    @Transactional
    public void deleteItem(int id) {
        itemRepository.deleteItemById(id);
    }

    public Item updateItem(int id, Item updatedItem) throws ResourceNotFoundException {

        Optional<Item> optionalItem=itemRepository.findById(id);
            if(optionalItem.isPresent()){

                Item item= optionalItem.get();
                item.setTitle(updatedItem.getTitle());
                item.setItemDetails(updatedItem.getItemDetails());
                return itemRepository.save(item);
            }else{
                throw new ResourceNotFoundException("Item not found with id " + id);
            }
     }



     @Transactional
    public  Item searchItem(int id) throws ResourceNotFoundException {
        if(id<= 0){
            throw new ResourceNotFoundException("item not found with id + "+id);
        }else
        {
              Item  item=itemRepository.findItemById(id);

        return item;
        }


    }

    // get itemDeatails
    public  Item_Details  findItemDetailsById(int id){

       return   itemDetailsRepository.findItem_DetailsById(id);
    }

    @Transactional
    public Item updateItem(int id, Item_Details itemDetails) {
        Item existingItem = findItemById(id);
        Item_Details existingItemDetails = (findItemDetailsById(existingItem.getId())) ;

        // Update item details
        existingItemDetails.setDescription(itemDetails.getDescription());
        existingItemDetails.setCreatedAt(itemDetails.getCreatedAt());
        existingItemDetails.setPriority(itemDetails.getPriority());
        existingItemDetails.setStatus(itemDetails.getStatus());
        existingItem.setItemDetails(existingItemDetails);

        String description = itemDetails.getDescription();
        int spaceIndex = description.indexOf(" ");
        String title = (spaceIndex != -1) ? description.substring(0, spaceIndex) : description;

        existingItem.setTitle(title);
        // Update item

        itemRepository.save(existingItem);

        return existingItem;
    }


}
