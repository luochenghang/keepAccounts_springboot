package com.lch.bills.controller;


import com.lch.bills.common.AjaxResponse;
import com.lch.bills.common.Anonymous;
import com.lch.bills.common.BaseController;
import com.lch.bills.service.UseHelpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @menu 帮助中心
 */
@RestController
@RequestMapping("/bills/help")
public class UseHelpController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(UseHelpController.class);


    @Autowired
    private UseHelpService useHelpService;

    /**
     * 获取账单类型
     * @return
     */
    @GetMapping("/getUseHelp")
    @Anonymous
    public AjaxResponse getUseHelp() {
        logger.info("getUseHelp:");
        try {
            return succees(useHelpService.getUseHelp());
        }catch (Exception e){
            return fail(e.getMessage());
        }
    }
}
