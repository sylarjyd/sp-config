package cn.tedu.sp04.order.dao;

import cn.tedu.sp04.order.entity.OrderPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDao extends JpaRepository<OrderPojo,String> {
}
