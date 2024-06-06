package com.toda.dao;

import com.toda.model.Item_Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemDetailsRepository extends JpaRepository<Item_Details,Integer> {


   Item_Details findItem_DetailsById(int id);
}
