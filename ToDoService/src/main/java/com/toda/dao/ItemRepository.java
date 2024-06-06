package com.toda.dao;

import com.toda.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item,Integer> {

     Item findItemById(int id);

     void deleteItemById(int id);
}
