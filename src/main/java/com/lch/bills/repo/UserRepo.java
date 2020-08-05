package com.lch.bills.repo;

import com.lch.bills.pojo.UserBase;
import com.lch.bills.pojo.vo.CountInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserRepo {

    @Select("SELECT id FROM t_user WHERE openid = #{openid}")
    Long getUserId(String openid);


    CountInfo CountStatistics(Long userId);

    @Select("SELECT * FROM t_user WHERE openid = #{openid}")
    UserBase getUserBaseByOpenid(String openid);

    int addUser(UserBase user);

    int updUser(UserBase user);
}
