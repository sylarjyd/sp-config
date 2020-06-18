package cn.tedu.sp02.item.dao;

import cn.tedu.sp02.item.entity.ItemPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemDao extends JpaRepository<ItemPojo,Integer> {

    @Modifying
    @Query(value = "update item_info set number = number - ?2 where id = ?1 and (number - ?2)>=0", nativeQuery = true)
    public int decreaseNumbers(Integer id, Integer number);
}
