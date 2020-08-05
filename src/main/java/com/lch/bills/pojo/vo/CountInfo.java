package com.lch.bills.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class CountInfo {

    private Integer totalDays; //开始记账到现在的天数

    private Integer totalKeepAccountsDays; // 记账总天数

    private Integer totalBillsNums; // 记账总数

    private Double totalPay; //总的支出

    private Double totalEarn; //总的收入


}
