package com.lch.bills.controller;


import com.lch.bills.common.AjaxResponse;
import com.lch.bills.common.Anonymous;
import com.lch.bills.common.BaseController;
import com.lch.bills.jwt.JwtInfo;
import com.lch.bills.jwt.JwtUtils;
import com.lch.bills.pojo.User;
import com.lch.bills.pojo.UserBillsType;
import com.lch.bills.service.UserBillsTypeService;
import com.lch.bills.service.UserService;
import com.lch.bills.utils.MD5Util;
import com.lch.bills.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @menu 用户
 */
@RestController
@RequestMapping("/bills/type")
public class UserBillsTypeController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(UserBillsTypeController.class);


    @Autowired
    private UserBillsTypeService userBillsTypeService;


    /**
     * 添加用户账单类型
     * @param userBillsType
     * @return
     */
    @PostMapping("/addUserBillsType")
    public AjaxResponse addUserBillsType(UserBillsType userBillsType) {
        logger.info("addUserBillsType: userBillsType={}", userBillsType);
        try {
            userBillsTypeService.addUserBillsType(userBillsType);
            return succees();
        }catch (Exception e){
            return fail(e.getMessage());
        }
    }

    /**
     * 删除账单类型
     * @param id
     * @return
     */
    @PostMapping("/delBillsType")
    public AjaxResponse delBillsType(Long id) {
        logger.info("delBillsType: id={}", id);
        try {
            return succees(userBillsTypeService.delBillsType(id));
        }catch (Exception e){
            return fail(e.getMessage());
        }
    }


    /**
     * 获取账单类型
     * @return
     */
    @GetMapping("/getUserBillsTypeList")
    public AjaxResponse getUserBillsTypeList(Integer type) {
        logger.info("getUserBillsTypeList:");
        try {
            return succees(userBillsTypeService.getUserBillsTypeList(type));
        }catch (Exception e){
            return fail(e.getMessage());
        }
    }

    /**
     * 排序账单类型
     * @param id
     * @param beforeId
     * @return
     */
    @PostMapping("/sortUserBillsType")
    public AjaxResponse sortUserBillsType(Long id, Long beforeId) {
        logger.info("sortUserBillsType: id={},beforeId={}", id,beforeId);
        try {
            return succees(userBillsTypeService.sortUserBillsType(id,beforeId));
        }catch (Exception e){
            return fail(e.getMessage());
        }
    }

}
