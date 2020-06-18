package cn.tedu.sp01.service;

import cn.tedu.sp01.pojo.User;

public interface UserService {
	User getUser(Integer id);
	int addScore(Integer id, Integer score) throws Exception;
}