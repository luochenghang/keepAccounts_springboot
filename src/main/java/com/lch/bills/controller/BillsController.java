package com.lch.bills.controller;


import com.lch.bills.common.AjaxResponse;
import com.lch.bills.common.Anonymous;
import com.lch.bills.common.BaseController;
import com.lch.bills.pojo.Bills;
import com.lch.bills.pojo.UserBillsType;
import com.lch.bills.service.BillsService;
import com.lch.bills.service.UserBillsTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @menu 用户
 */
@RestController
@RequestMapping("/bills/bills")
public class BillsController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(BillsController.class);


    @Autowired
    private BillsService billsService;


    /**
     * 添加用户账单
     * @param bills
     * @return
     */
    @PostMapping("/addBills")
    public AjaxResponse addBills(Bills bills) {
        logger.info("addBills: bills={}", bills);
        try {
            return succees(billsService.save(bills));
        }catch (Exception e){
            return fail(e.getMessage());
        }
    }

    /**
     * 删除账单
     * @param id
     * @return
     */
    @PostMapping("/delBills")
    public AjaxResponse delBills(Long id) {
        logger.info("delBills: id={}", id);
        try {
            return succees(billsService.delBills(id));
        }catch (Exception e){
            return fail(e.getMessage());
        }
    }


    /**
     * 获取每个月的账单
     * @return
     */
    @GetMapping("/getBillsList")
    public AjaxResponse getBillsList(Date createDate) {
        logger.info("getBillsList:");
        try {
            return succees(billsService.getBillsList(createDate));
        }catch (Exception e){
            return fail(e.getMessage());
        }
    }


}
