package com.lch.bills.controller;


import com.lch.bills.common.AjaxResponse;
import com.lch.bills.common.Anonymous;
import com.lch.bills.common.App;
import com.lch.bills.common.BaseController;
import com.lch.bills.common.exceptions.ServiceException;
import com.lch.bills.jwt.JwtInfo;
import com.lch.bills.jwt.JwtUtils;
import com.lch.bills.pojo.UlBo;
import com.lch.bills.pojo.User;
import com.lch.bills.service.UserBillsTypeService;
import com.lch.bills.service.UserService;
import com.lch.bills.utils.MD5Util;
import com.lch.bills.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @menu 用户
 */
@RestController
@RequestMapping("/bills/user")
public class UserController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(com.lch.bills.controller.UserController.class);


    @Autowired
    private UserService userService;

    @Autowired
    private UserBillsTypeService userBillsTypeService;


    @Anonymous
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public AjaxResponse login(UlBo bo) throws Exception {
        Map<String, Object> map = userService.login(bo, null, App.BILLS_APPID, App.BILLS_APP_SECRET);
        //这里需要初始化一下数据 登录成功
        userBillsTypeService.init(Long.valueOf(map.get("userId").toString()));
        return succees(map);
    }

//    /**
//     * 登录接口
//     *
//     * @param userName
//     * @param password
//     * @return
//     */
//    @PostMapping("/login")
//    @Anonymous
//    public AjaxResponse login(String userName, String password) {
//        logger.info("login: userName={}, password={}", userName, password);
//
//        if (StringUtils.isBlank(userName))
//            return fail("用户不得为空");
//
//        if (StringUtils.isBlank(password))
//            return fail("密码不得为空");
//
//        try {
//            User user = userService.findByUserName(userName);
//            if (user == null)
//                return fail("用户不存在");
//
//            // 验证密码是否正确
//            if (!MD5Util.validPassword(password, user.getPassword()))
//                return fail("密码错误");
//
//            //这里需要初始化一下数据 登录成功
//            userBillsTypeService.init(user.getId());
//
//            String token = JwtUtils.generatorToken(new JwtInfo(String.valueOf(user.getId())));
//            return succees(token);
//        } catch (Exception e) {
//            return fail(e.getMessage());
//        }
//    }



    @GetMapping("")
    public AjaxResponse CountStatistics() {
        logger.info("CountStatistics");
        try {

            return succees(userService.CountStatistics());
        }catch (Exception e){
            return fail(e.getMessage());
        }
    }


    /*************************************************************/
    //测试接口，登录状态
    @GetMapping("get")
    public AjaxResponse list() {

        return succees(11);
    }

    @GetMapping("getMe")
    @Anonymous
    public AjaxResponse getMe() {

        return succees(22);
    }
}
