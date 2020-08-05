package com.lch.bills.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Accessors(chain = true)
@Data
public class BillsType {

    private Long id;

    //类型名称
    private String name;

    //图片地址路径，保存在前端
    private String imgUrl;

    //类型，0公共的 1支出 2收入
    private Integer type;

    private Date createDate;

    private Date updateDate;


}
