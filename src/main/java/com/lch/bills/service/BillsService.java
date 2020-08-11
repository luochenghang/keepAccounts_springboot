package com.lch.bills.service;

import com.lch.bills.pojo.Bills;
import com.lch.bills.pojo.vo.ChartData;
import com.lch.bills.pojo.vo.EveryDayBills;
import com.lch.bills.pojo.vo.SonMenu;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface BillsService {


    int save(Bills bills);

    int delBills(Long id);

    List<EveryDayBills> getBillsList(Date createDate);

    Bills getBills(Long id);

    int editBills(Bills bills);

    //点击图表中的周月年

    /**
     *
     * @param //type 1支出 2收入
     * @param //距离当前时间第几周 或者几月份 或者年份 具体的看dateType
     * @param dateType  1周 2 月 3年
     * @return
     */
    List<SonMenu> getWeek(Integer dateType) throws ParseException;


    List<ChartData> getChartsData(Integer type, Integer dateType, String days) throws ParseException;

    List<Bills> getChartsDataDetail(Integer type, Integer dateType, String days) throws ParseException;

    List<Bills> getBillsByRemakeAndTypeId(Integer type, Integer dateType, String days, String remake, Long userBillsTypeId) throws ParseException;
}
