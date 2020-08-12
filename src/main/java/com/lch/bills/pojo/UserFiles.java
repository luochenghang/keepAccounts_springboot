package com.lch.bills.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
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


	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss",timezone = "GMT+8")
	private Date createDate;

	

}
