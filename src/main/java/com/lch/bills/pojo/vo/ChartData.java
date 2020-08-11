package com.lch.bills.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class ChartData {

    private String days; //时间

    private Double sumMoney; // 总金额




}
