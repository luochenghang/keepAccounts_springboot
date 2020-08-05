package com.lch.bills.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 *
 * @author yushanzi@wacai.com
 * @date 2015-11-10 14:04
 */
public class CookieUtil {
	private static final Log log = LogFactory.getLog(com.lch.bills.utils.CookieUtil.class);

	public static String getAccessTokenFromCookie(HttpServletRequest request, String key) {
		Cookie[] cookies = request.getCookies();
		if (ArrayUtils.isNotEmpty(cookies)) {
			for (Cookie cookie : cookies) {
				if (StringUtils.equals(cookie.getName(), key)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}


	public static String getAccessTokenFromRequest(HttpServletRequest request, String key) {
		return request.getHeader(key);
	}

	public static Cookie genCookieWithDomain(String key, String value, int maxAge, String domain){
		Cookie cookie = new Cookie(key, value);
		enrichCookie(cookie, "/", maxAge, domain);
		return cookie;
	}

	public static Cookie genCookie(String key, String value, String uri, int maxAge, String domain){
		Cookie cookie = new Cookie(key, value);
		enrichCookie(cookie, uri, maxAge, domain);
		return cookie;
	}

	public static void enrichCookie(Cookie cookie, String uri, int maxAge, String domain){
		log.debug("cookie path is=[" + uri + "], maxAge=[" + maxAge + "], domain=[" + domain + "]");
		cookie.setPath(uri);
		cookie.setMaxAge(maxAge);
		cookie.setDomain(domain);
	}

	public static void setCookie(HttpServletResponse response, Cookie cookie){
		response.addCookie(cookie);
	}


	public static boolean isAjax(HttpServletRequest request){
		boolean isAjaxRequest = false;
		if(!StringUtils.isBlank(request.getHeader("x-requested-with")) && request.getHeader("x-requested-with").equals("XMLHttpRequest")){
			isAjaxRequest = true;
		}
		return isAjaxRequest;
	}
}