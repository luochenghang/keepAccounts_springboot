package com.lch.bills.pojo;

import lombok.Data;

@Data
public class UseHelp {

    private Long id;

    private String problem;//问题

    private String answer;//回答

    private  int sort; //排序 越小越靠前
}
