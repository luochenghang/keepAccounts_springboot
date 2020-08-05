package com.lch.bills.pojo.vo;

import com.lch.bills.pojo.Bills;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Accessors(chain = true)
@Data
public class EveryDayBills {

    private Bills bills;

    private Date createDate;

    private String weeks; //星期数

    private Double everyDaySumMoney; //每天的总金额  负数表示支出 正数表示收入


}
