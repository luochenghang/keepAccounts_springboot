package com.lch.bills.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


import java.util.Date;


@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	private Long id;
	//登录名
    private String userName;

    private String password;

	private String coverImg; //封面图路径  为null表示默认图像

	private Integer sex; //0保密 1男 2女

	private Date createDate;

	private Date updateDate;
	

}
