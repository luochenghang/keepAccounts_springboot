package com.lch.bills.jwt;

import io.jsonwebtoken.Claims;
import lombok.Data;

/**
 * 验证信息 
 * 创建者  科帮网 
 * 创建时间  2017年11月27日
 */
@Data
public class CheckResult {

	private String uid;

	private String msg;

	private String code;

	private boolean success;

	private Claims claims;


}
