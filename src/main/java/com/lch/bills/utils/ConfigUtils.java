package com.lch.bills.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @ClassName: ConfigUtils
 * @Description: 获取配置文件类
 * @date 2018年10月24日 下午4:50:55
 */
public class ConfigUtils {

	private static com.lch.bills.utils.ConfigUtils instance;

	private static Logger logger = LoggerFactory.getLogger(com.lch.bills.utils.ConfigUtils.class);

	private Properties properties;

	private ConfigUtils() {
		init();
	}

	public static synchronized com.lch.bills.utils.ConfigUtils getInstance() {
		if (instance == null) {
			instance = new com.lch.bills.utils.ConfigUtils();
		}
		return instance;
	}

	private void init() {
		properties = new Properties();
		try {
			String active = new PropertiesLoader("application.yml").getProperty("spring.profiles.active");
			properties.load(com.lch.bills.utils.ConfigUtils.class.getResourceAsStream("/application-"+active+".properties"));
		}
		catch (IOException e) {
			logger.error("Error occurs while loading common.properties.", e);
		}
	}

	public String getConfigValue(String configName) {
		return properties.getProperty(configName, "");
	}
	
	public Integer getIntegerValue(String configName) {
		return ObjectUtils.toInteger(getConfigValue(configName));
	}
	
	public Double getDoubleValue(String configName) {
		return ObjectUtils.toDouble(getConfigValue(configName));
	}
	
	public Boolean getBooleanValue(String configName) {
		return ObjectUtils.toBoolean(getConfigValue(configName));
	}
	
	public List<String> getListValue(String configName){
		List<String> list = new ArrayList<>();
		String value = getConfigValue(configName);
		if(StringUtils.isNotBlank(value)) {
			return Arrays.asList(value.split(","));
		}
		return list;
	}
}
