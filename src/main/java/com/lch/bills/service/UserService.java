package com.lch.bills.service;

import com.lch.bills.common.exceptions.ServiceException;
import com.lch.bills.pojo.UlBo;
import com.lch.bills.pojo.vo.CountInfo;

import java.util.Map;

public interface UserService {


    /**
     * 用户登录
     *
     * @param vo
     * @param src
     * @param appid
     * @param appSecret
     * @return
     * @throws ServiceException
     */
    Map<String, Object> login(UlBo vo, String src, String appid, String appSecret) throws ServiceException, Exception;

//    /**
//     * 根据用户名查询用户
//     * @param userName
//     * @return
//     */
//    User findByUserName(String userName);
//
//    void register(User user) throws Exception;
//
//    void updPassword(String oldPwd, String newPwd) throws Exception;

    CountInfo CountStatistics();

    int addUserFiles() throws Exception;
}
