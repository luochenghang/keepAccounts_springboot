package com.lch.bills.controller;


import com.lch.bills.common.AjaxResponse;
import com.lch.bills.common.Anonymous;
import com.lch.bills.common.BaseController;
import com.lch.bills.pojo.Bills;
import com.lch.bills.pojo.UserBillsType;
import com.lch.bills.service.BillsService;
import com.lch.bills.service.UserBillsTypeService;
import com.lch.bills.utils.RegexUtils;
import com.lch.bills.utils.StringUtils;
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
     *
     * @param bills
     * @return
     */
    @PostMapping("/addBills")
    public AjaxResponse addBills(Bills bills) {
        logger.info("addBills: bills={}", bills);
        try {
            return succees(billsService.save(bills));
        } catch (Exception e) {
            return fail(e.getMessage());
        }
    }

    @PostMapping("/editBills")
    public AjaxResponse editBills(Bills bills) {
        logger.info("editBills: bills={}", bills);
        try {
            return succees(billsService.editBills(bills));
        } catch (Exception e) {
            return fail(e.getMessage());
        }
    }

    /**
     * 删除账单
     *
     * @param id
     * @return
     */
    @PostMapping("/delBills")
    public AjaxResponse delBills(Long id) {
        logger.info("delBills: id={}", id);
        try {
            return succees(billsService.delBills(id));
        } catch (Exception e) {
            return fail(e.getMessage());
        }
    }

    /**
     * 根据id获取账单
     *
     * @return
     */
    @GetMapping("/getBills")
    public AjaxResponse getBills(Long id) {
        logger.info("getBills:");
        try {
            return succees(billsService.getBills(id));
        } catch (Exception e) {
            return fail(e.getMessage());
        }
    }

    /**
     * 获取每个月的账单
     *
     * @return
     */
    @GetMapping("/getBillsList")
    public AjaxResponse getBillsList(Date createDate) {
        logger.info("getBillsList:");
        try {
            return succees(billsService.getBillsList(createDate));
        } catch (Exception e) {
            return fail(e.getMessage());
        }
    }

    /**
     * 根据周月年 或者对应下面的日期数
     *
     * @param dateType 1周 2月 3年
     * @return
     */
    @GetMapping("/getWeekOrDays")
    public AjaxResponse getWeekOrDays(Integer dateType) {
        logger.info("getWeekOrDays:dateType={}", dateType);
        if (dateType == null) {
            dateType = 1;
        }
        if (!StringUtils.isAppointParam(dateType, 1, 2, 3)) {
            return fail("参数异常，不能是1,2,3以外的数字");
        }

        try {
            return succees(billsService.getWeek(dateType));
        } catch (Exception e) {
            return fail(e.getMessage());
        }
    }


    /**
     * 获取图表数据
     *
     * @param type     1支出 2收入
     * @param dateType 1周 2月 3年
     * @param days     具体哪一天 或者哪一年 具体看dateType
     * @return
     */
    @GetMapping("/getChartsData")
    public AjaxResponse getChartsData(Integer type, Integer dateType, String days) {
        logger.info("getChartsData:type={},dateType={},days={}", type, dateType, days);
        if (dateType == null) {
            dateType = 1;
        }
        if (type == null) {
            type = 1;
        }
        if (!StringUtils.isAppointParam(dateType, 1, 2, 3)) {
            return fail("dateType参数异常，不能是1,2,3以外的数字");
        }
        if (!StringUtils.isAppointParam(type, 1, 2)) {
            return fail("type参数异常，不能是1,2以外的数字");
        }
        try {
            return succees(billsService.getChartsData(type, dateType, days));
        } catch (Exception e) {
            return fail(e.getMessage());
        }
    }

    /**
     * 获取图表数据排行榜数据
     *
     * @param type     1支出 2收入
     * @param dateType 1周 2月 3年
     * @param days     具体哪一天 或者哪一年 具体看dateType
     * @return
     */
    @GetMapping("/getChartsDataDetail")
    public AjaxResponse getChartsDataDetail(Integer type, Integer dateType, String days) {
        logger.info("getChartsDataDetail:type={},dateType={},days={}", type, dateType, days);
        if (dateType == null) {
            dateType = 1;
        }
        if (type == null) {
            type = 1;
        }
        if (!StringUtils.isAppointParam(dateType, 1, 2, 3)) {
            return fail("dateType参数异常，不能是1,2,3以外的数字");
        }
        if (!StringUtils.isAppointParam(type, 1, 2)) {
            return fail("type参数异常，不能是1,2以外的数字");
        }
        try {
            return succees(billsService.getChartsDataDetail(type, dateType, days));
        } catch (Exception e) {
            return fail(e.getMessage());
        }
    }

    /**
     * 获取图表数据排行榜数据
     *
     * @param type            1支出 2收入
     * @param dateType        1周 2月 3年
     * @param days            具体哪一天 或者哪一年 具体看dateType
     * @param remake          备注
     * @param userBillsTypeId 类型id
     * @return
     */
    @GetMapping("/getBillsByRemakeAndTypeId")
    public AjaxResponse getBillsByRemakeAndTypeId(Integer type, Integer dateType, String days, String remake, Long userBillsTypeId) {
        logger.info("getBillsByRemakeAndTypeId:type={},dateType={},days={},remake={},userBillsTypeId={}", type, dateType, days, remake, userBillsTypeId);
        if (dateType == null) {
            dateType = 1;
        }
        if (type == null) {
            type = 1;
        }
        if (!StringUtils.isAppointParam(dateType, 1, 2, 3)) {
            return fail("dateType参数异常，不能是1,2,3以外的数字");
        }
        if (!StringUtils.isAppointParam(type, 1, 2)) {
            return fail("type参数异常，不能是1,2以外的数字");
        }
        try {
            return succees(billsService.getBillsByRemakeAndTypeId(type, dateType, days,remake,userBillsTypeId));
        } catch (Exception e) {
            return fail(e.getMessage());
        }
    }


}
