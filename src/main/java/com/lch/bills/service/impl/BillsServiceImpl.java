package com.lch.bills.service.impl;

import com.lch.bills.pojo.Bills;
import com.lch.bills.pojo.vo.EveryDayBills;
import com.lch.bills.repo.BillsRepo;
import com.lch.bills.service.BillsService;
import com.lch.bills.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BillsServiceImpl implements BillsService {

   @Autowired
    private BillsRepo billsRepo;


    @Override
    public int save(Bills bills) {
        bills.setUserId(UserUtils.getCurrentUserId());
        return billsRepo.save(bills);
    }

    @Override
    public int delBills(Long id) {
        return billsRepo.delBills(id,UserUtils.getCurrentUserId());
    }

    @Override
    public List<EveryDayBills> getBillsList(Date createDate) {
        return billsRepo.getBillsList(createDate,UserUtils.getCurrentUserId());
    }
}
