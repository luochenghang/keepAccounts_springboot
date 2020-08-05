package com.lch.bills.pojo;

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

    private Date createDate;

    private Date updateDate;



}
