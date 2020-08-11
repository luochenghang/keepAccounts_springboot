package com.lch.bills.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Accessors(chain = true)
@Data
public class UserBillsType {

    private Long id;


    private Long userId;


    private Long billsTypeId;

    //自定义的名称 名称不超过四个字
    private String name;

    private String imgUrl;

    //排序编号 越小越靠前
    private Integer sort;

    //类型，1支出 2收入
    private Integer type;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date updateDate;


}
