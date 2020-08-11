package com.lch.bills.service.impl;

import com.lch.bills.pojo.Bills;
import com.lch.bills.pojo.vo.ChartData;
import com.lch.bills.pojo.vo.EveryDayBills;
import com.lch.bills.pojo.vo.SonMenu;
import com.lch.bills.repo.BillsRepo;
import com.lch.bills.service.BillsService;
import com.lch.bills.utils.DateUtils;
import com.lch.bills.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BillsServiceImpl implements BillsService {

    @Autowired
    private BillsRepo billsRepo;


    @Override
    public int save(Bills bills) {
        bills.setUserId(UserUtils.getCurrentUserId());
        //支出为负数 收入为正数
        if (bills.getType() == 1) {
            bills.setMoney(-bills.getMoney());
        }
        return billsRepo.save(bills);
    }

    @Override
    public int delBills(Long id) {
        return billsRepo.delBills(id, UserUtils.getCurrentUserId());
    }

    @Override
    public List<EveryDayBills> getBillsList(Date createDate) {
        if (createDate == null) {
            createDate = new Date();
        }
        return billsRepo.getBillsList(createDate, UserUtils.getCurrentUserId());
    }

    @Override
    public Bills getBills(Long id) {
        return billsRepo.getBills(id);
    }

    @Override
    public int editBills(Bills bills) {
        bills.setUserId(UserUtils.getCurrentUserId());

        Bills billsDB = billsRepo.getBills(bills.getId());
        //支出为负数 收入为正数
        if (billsDB.getType() == 1) {
            bills.setMoney(-Math.abs(bills.getMoney()));
        }
        //当初始状态存在备注的时候，备注可以为空 但是当初始状态备注为空的时候，没有修改则不用更新
        if (billsDB.getRemake() == null && bills.getRemake().equals("")) {
            bills.setRemake(null);
        }

        return billsRepo.editBills(bills);
    }

    @Override
    public List<SonMenu> getWeek(Integer dateType) throws ParseException {

        Long userId = UserUtils.getCurrentUserId();
        Bills billsMinDate = billsRepo.getBillsMinDate(userId);
        Bills billsMaxDate = billsRepo.getBillsMaxDate(userId);

        Date minDate = null;
        Date maxDate = null;
        if (billsMinDate != null && billsMaxDate != null) {
            minDate = billsMinDate.getMinDate();
            maxDate = billsMaxDate.getMaxDate();
        }

        List<Map.Entry<String, String>> result = new ArrayList<Map.Entry<String, String>>();
        if (dateType == 1) {//周
            result = DateUtils.getWeekMap(minDate, maxDate);
        }

        if (dateType == 2) {//月
            result = DateUtils.getMonthMap(minDate, maxDate);
        }

        if (dateType == 3) {//年
            result = DateUtils.getYearMap(minDate, maxDate);

        }
        List<SonMenu> sonMenus = new ArrayList<>();
        for (Map.Entry<String, String> map :result){
            SonMenu sonMenu = new SonMenu();
            sonMenu.setDays(map.getKey()).setName(map.getValue());
            sonMenus.add(sonMenu);
        }

        return sonMenus;
    }

    @Override
    public List<ChartData> getChartsData(Integer type, Integer dateType, String days) throws ParseException {
        Long userId = UserUtils.getCurrentUserId();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<ChartData> chartData = null;
        if (days.equals("")){
           // days =
        }

        if (dateType == 1) {//周
            chartData = billsRepo.everyWeekData(days, type, userId);
        }

        if (dateType == 2) {//月
            Date createDate = sdf.parse(days+"-01"); //此时days为2020-01
            chartData = billsRepo.everyMonthData(createDate,type,userId);
        }

        if (dateType == 3) {//年
            chartData = billsRepo.everyYearData(Integer.parseInt(days),type,userId);

        }
        return chartData;
    }

    @Override
    public List<Bills> getChartsDataDetail(Integer type, Integer dateType, String days) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = "";
        String endDate="";
        Long userId = UserUtils.getCurrentUserId();
        if (dateType == 1){
            startDate = days;
            Date date = sdf.parse(days);
            String timeInterval = DateUtils.getTimeInterval(date); //获取data日期的 周一，周日
            endDate =timeInterval.split(",")[1];
        }
        if (dateType == 2){
            startDate = days+ "-01";
            endDate = DateUtils.getLastDay(days);
        }
        if (dateType == 3){
            startDate = days+ "-01-01";
            endDate = days+ "-12-31";
        }

        return billsRepo.chartsDataDetail(startDate,endDate,type,userId);
    }

    @Override
    public List<Bills> getBillsByRemakeAndTypeId(Integer type, Integer dateType, String days, String remake, Long userBillsTypeId) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = "";
        String endDate="";
        Long userId = UserUtils.getCurrentUserId();
        if (dateType == 1){
            startDate = days;
            Date date = sdf.parse(days);
            String timeInterval = DateUtils.getTimeInterval(date); //获取data日期的 周一，周日
            endDate =timeInterval.split(",")[1];
        }
        if (dateType == 2){
            startDate = days+ "-01";
            endDate = DateUtils.getLastDay(days);
        }
        if (dateType == 3){
            startDate = days+ "-01-01";
            endDate = days+ "-12-31";
        }

        return billsRepo.getBillsByRemakeAndTypeId(startDate,endDate,type,userId,remake,userBillsTypeId);
    }
}
