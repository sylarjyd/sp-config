package cn.tedu.sp03.user.dao;

import cn.tedu.sp03.user.entity.UserPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<UserPojo,Integer> {
    @Modifying
    @Query(value = "update user_info set score = score + ?1 where id = ?2", nativeQuery = true)
    public int addScore(Integer score,Integer id);
}
