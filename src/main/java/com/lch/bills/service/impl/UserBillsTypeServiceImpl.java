package com.lch.bills.service.impl;

import com.lch.bills.pojo.BillsType;
import com.lch.bills.pojo.UserBillsType;
import com.lch.bills.repo.BillsRepo;
import com.lch.bills.repo.UserBillsTypeRepo;
import com.lch.bills.service.UserBillsTypeService;
import com.lch.bills.utils.RegexUtils;
import com.lch.bills.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBillsTypeServiceImpl implements UserBillsTypeService {

    @Autowired
    private UserBillsTypeRepo userBillsTypeRepo;

    @Autowired
    private BillsRepo billsRepo;

    @Override
    public void addUserBillsType(UserBillsType userBillsType) throws Exception {
        userBillsType.setUserId(UserUtils.getCurrentUserId());
        if (RegexUtils.getWordCount(userBillsType.getName())>8){
            throw new Exception("类型名称且不超过4个字");
        }
        BillsType systemBills = userBillsTypeRepo.getSystemBills(userBillsType.getBillsTypeId());
        if (systemBills == null)
            throw new Exception("类型选择异常");

        userBillsType.setImgUrl(systemBills.getImgUrl());
        userBillsTypeRepo.save(userBillsType);
    }

    @Override
    public int delBillsType(Long id) {
        Long userId = UserUtils.getCurrentUserId();
        //删除类型将删除该类型下的所有账单
        Long userBillsTypeId = id;
        billsRepo.delBillsByTypeId(userBillsTypeId,userId);
        return userBillsTypeRepo.delBillsType(id, userId);
    }

    @Override
    public List<UserBillsType> getUserBillsTypeList(Integer type) {
        return userBillsTypeRepo.getUserBillsTypeList(UserUtils.getCurrentUserId(), type);
    }

    @Override
    public int sortUserBillsType(Long id, Long beforeId) {

        //todo 排序
        return 0;
    }

    @Override
    public Integer init(Long id) {
        //首先根据用户id来初始化用户的账单类型
        //判断用户是否已经初始化了
        if (userBillsTypeRepo.getUserBillsTypeList(id,1).size()>0 && userBillsTypeRepo.getUserBillsTypeList(id,2).size()>0){
            //存在数据则表明已经初始化了，直接退出
            return null;
        }
        //获取账单类型的初始对象
        List<BillsType> initBillsTypes = userBillsTypeRepo.getInitBillsType();
        for (BillsType initBillsType : initBillsTypes) {
            UserBillsType userBillsType = new UserBillsType();
            userBillsType.setUserId(id).setBillsTypeId(initBillsType.getId()).setName(initBillsType.getName())
            .setImgUrl(initBillsType.getImgUrl()).setType(initBillsType.getType());
            userBillsTypeRepo.save(userBillsType);
        }
        return null;
    }

    @Override
    public List<BillsType> getSystemBillsTypeList() {
        return userBillsTypeRepo.getSystemBillsTypeList();
    }
}
