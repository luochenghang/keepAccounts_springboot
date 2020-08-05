package com.lch.bills.service;

import com.lch.bills.pojo.UserBillsType;

import java.util.List;

public interface UserBillsTypeService {


    void addUserBillsType(UserBillsType userBillsType) throws Exception;

    int delBillsType(Long id);

    List<UserBillsType> getUserBillsTypeList(Integer type);

    int sortUserBillsType(Long id, Long beforeId);

    Integer init(Long id);
}
