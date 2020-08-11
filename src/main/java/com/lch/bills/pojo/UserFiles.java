package com.lch.bills.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

//用户通知实体
@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFiles {

	private Long id;

    private Long userId;

    private String filePath;//文件路径

	//备注
	private String remarks;

	private Date createDate;

	

}
