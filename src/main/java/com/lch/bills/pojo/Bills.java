package com.lch.bills.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Accessors(chain = true)
@Data
public class Bills extends UserBillsType{

    private Long id;

    private Long userBillsTypeId; //用户账单类型id

    private Long userId;

    private Integer type;//1 支出 2收入

    private Double money; //金钱数量

    private String remake; //备注

    //临时数据
    private Date minDate;

    private Date maxDate;


    private Double sumMoney; // 在具体某一段时间内同一类型的总金额数
    private Double percentMoney;//金额所占比例





}
