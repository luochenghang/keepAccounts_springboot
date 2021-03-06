package com.lch.bills.repo;

import com.lch.bills.pojo.UserBase;
import com.lch.bills.pojo.UserFiles;
import com.lch.bills.pojo.vo.CountInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserRepo {

    @Select("SELECT id FROM t_user WHERE openid = #{openid}")
    Long getUserId(String openid);


    CountInfo CountStatistics(Long userId);

    @Select("SELECT * FROM t_user WHERE openid = #{openid}")
    UserBase getUserBaseByOpenid(String openid);

    @Select("SELECT * FROM t_user WHERE id = #{userId}")
    UserBase get(Long userId);

    int addUser(UserBase user);

    int updUser(UserBase user);

    Integer addUserFiles(UserFiles userFiles);

    @Select("select count(1) from t_user_files where userId = #{userId} and to_days(createDate)=to_days(now())")
    int getCountUserFiles(Long userId);

    @Select("select * from t_user_files where userId = #{userId} order by createDate desc")
    List<UserFiles> getUserFilesList(Long userId);
}
