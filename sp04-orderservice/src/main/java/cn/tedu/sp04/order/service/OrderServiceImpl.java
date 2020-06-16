package cn.tedu.sp04.order.service;

import cn.tedu.sp01.pojo.Item;
import cn.tedu.sp01.pojo.Order;
import cn.tedu.sp01.pojo.User;
import cn.tedu.sp01.service.OrderService;
import cn.tedu.sp04.order.dao.OrderDao;
import cn.tedu.sp04.order.entity.OrderPojo;
import cn.tedu.sp04.order.feignclient.ItemFeignService;
import cn.tedu.sp04.order.feignclient.UserFeignService;
import cn.tedu.web.util.JsonResult;
import com.codingapi.txlcn.tc.annotation.DTXPropagation;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private ItemFeignService itemService;
	@Autowired
	private UserFeignService userService;
	@Autowired
	private OrderDao orderDao;

	@Override
	public Order getOrder(String orderId) {
		//调用user-service获取用户信息
		JsonResult<User> user = userService.getUser(7);

		//调用item-service获取商品信息
		JsonResult<List<Item>> items = itemService.getItems(orderId);


		Order order = new Order();
		order.setId(orderId);
		order.setUser(user.getData());
		order.setItems(items.getData());
		return order;
	}

	@LcnTransaction(propagation = DTXPropagation.REQUIRED) //分布式事务注解
	@Transactional
	@Override
	public void addOrder(Order order) {
		//调用item-service减少商品库存
		itemService.decreaseNumber(order.getItems());

		Integer userid = order.getUser().getId();
		//调用user-service增加用户积分
		userService.addScore(userid, 100);

		log.info("保存订单："+order);
		List<Item> items = order.getItems();
		List<OrderPojo> orderPojos = new ArrayList<>();

		items.stream().forEach(e->{
			OrderPojo orderPojo = new OrderPojo();
			orderPojo.setUserid(userid)
					.setItemid(e.getId())
					.setNum(e.getNumber())
					.setAddtime(LocalDateTime.now())
					.setModifytime(LocalDateTime.now());
			orderPojos.add(orderPojo);
		});
		orderDao.saveAll(orderPojos);
		int m = 1/0;
	}

}