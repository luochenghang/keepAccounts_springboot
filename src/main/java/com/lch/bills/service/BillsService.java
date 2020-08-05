package com.lch.bills.service;

import com.lch.bills.pojo.Bills;
import com.lch.bills.pojo.vo.EveryDayBills;

import java.util.Date;
import java.util.List;

public interface BillsService {


    int save(Bills bills);

    int delBills(Long id);

    List<EveryDayBills> getBillsList(Date createDate);
}
