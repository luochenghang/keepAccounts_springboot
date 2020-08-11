package com.lch.bills.service.impl;

import com.lch.bills.common.exceptions.ServiceException;
import com.lch.bills.common.wechat.WeChatSession;
import com.lch.bills.common.wechat.WeChatUtils;
import com.lch.bills.jwt.JwtInfo;
import com.lch.bills.jwt.JwtUtils;
import com.lch.bills.pojo.Bills;
import com.lch.bills.pojo.UlBo;
import com.lch.bills.pojo.UserBase;
import com.lch.bills.pojo.UserFiles;
import com.lch.bills.pojo.vo.CountInfo;
import com.lch.bills.repo.BillsRepo;
import com.lch.bills.repo.UserRepo;
import com.lch.bills.service.UserService;
import com.lch.bills.utils.ExcelUtils;
import com.lch.bills.utils.StringUtils;
import com.lch.bills.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    private Logger log = LoggerFactory.getLogger(com.lch.bills.controller.UserController.class);
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BillsRepo billsRepo;

    @Override
    public Map<String, Object> login(UlBo vo, String src, String appid, String appSecret) throws Exception {
        //保存用户信息
        UserBase user = this.save(vo, src, appid, appSecret);
        //用户登录
        String token = JwtUtils.generatorToken(new JwtInfo(String.valueOf(user.getId())));
        // 存在用户id，则保存
        //UserUtils.setCurrentUserId(Long.valueOf(uid));
        //封装返回值
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("userId", user.getId());
        return map;
    }


    @Override
    public CountInfo CountStatistics() {
        return userRepo.CountStatistics(UserUtils.getCurrentUserId());
    }

    @Override
    public int addUserFiles() throws Exception {
        UserFiles userFiles = new UserFiles();
        Long userId = UserUtils.getCurrentUserId();

        int countUserFiles = userRepo.getCountUserFiles(userId);
        if (countUserFiles > 3) {
            throw new Exception("每日只能导出三次记录");
        }

        List<Bills> list = billsRepo.getAllBillsList(userId);
        String filePath = ExcelUtils.createExcel(list);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());
        String remarks = "这是" +today + "导出文件第" + (countUserFiles+1) + "次";
        userFiles.setUserId(userId).setFilePath(filePath).setRemarks(remarks);

        return userRepo.addUserFiles(userFiles);
    }


    /**
     * 保存用户信息
     *
     * @param src
     * @param appid
     * @param appSecret
     * @return
     * @throws ServiceException
     */
    @Transactional(readOnly = false)
    public UserBase save(UlBo bo, String src, String appid, String appSecret) throws Exception {
        // 解析用户openid
        String openid = this.decipherOpenid(bo, appid, appSecret);
        // 判断用户信息是否存在
        UserBase userBase = userRepo.getUserBaseByOpenid(openid);
        // 若不存在则新增
        UserBase user = new UserBase();
        if (userBase == null) {
            // 新增基本用户信息
            user.setOpenid(openid);
            user.setNickName(bo.getNickName());
            user.setPortrait(bo.getPortrait());
            user.setSex(bo.getSex());
            userRepo.addUser(user);
        } else {
            // 若存在，则更新基本信息
            user.setId(userBase.getId());
            user.setNickName(bo.getNickName());
            user.setPortrait(bo.getPortrait());
            user.setSex(bo.getSex());
            userRepo.updUser(user);
        }

        return user;
    }

    /**
     * 解析微信用户数据
     *
     * @param bo
     * @param appid
     * @param appSecret
     * @throws ServiceException
     */
    protected String decipherOpenid(UlBo bo, String appid, String appSecret) throws Exception {
        WeChatSession chat = WeChatUtils.login(bo.getCode(), appid, appSecret);
        String openid = null;
        if (chat == null || StringUtils.isBlank(openid = chat.getOpenid())) {
            log.error("小程序登录获取openid失败，微信登录接口返回参数为：{}", chat.toString());
            throw new Exception("小程序登录失败，请稍后重试");
        }
        bo.setOpenid(openid);
        bo.setUnionid(chat.getUnionid());
        bo.setSessionKey(chat.getSession_key());
        return openid;
    }
}
