package com.lch.bills.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lch.bills.pojo.Bills;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;


@Accessors(chain = true)
@Data
public class EveryDayBills {

    private Long userId;

    private List<Bills> bills;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createDate;

    private String weeks; //星期数

    private Double everyDaySumMoney; //每天的总金额  负数表示支出 正数表示收入

    private Double everyDayPayIn;

    private Double everyDayPayOut;


}
