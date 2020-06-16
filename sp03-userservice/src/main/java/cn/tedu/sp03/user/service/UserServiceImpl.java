package cn.tedu.sp03.user.service;

import cn.tedu.sp01.pojo.User;
import cn.tedu.sp01.service.UserService;
import cn.tedu.sp03.user.dao.UserDao;
import cn.tedu.web.util.JsonUtil;
import com.codingapi.txlcn.tc.annotation.DTXPropagation;
import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RefreshScope
@Slf4j
@Service
public class UserServiceImpl implements UserService {
	@Value("${sp.user-service.users}")
	private String userJson;

	@Autowired
	private UserDao userDao;

	@Override
	public User getUser(Integer id) {
		log.info("users json string : " + userJson);
		List<User> list = JsonUtil.from(userJson, new TypeReference<List<User>>() {
		});
		for (User u : list) {
			if (u.getId().equals(id)) {
				return u;
			}
		}

		return new User(id, "name-" + id, "pwd-" + id);
	}

	@LcnTransaction(propagation = DTXPropagation.REQUIRED) //分布式事务注解
	@Transactional
	@Override
	public void addScore(Integer id, Integer score){
		// 这里增加积分
		userDao.addScore(score,id);
		log.info("user " + id + " - 增加积分 " + score);
//		int m = 1/0;
	}

}