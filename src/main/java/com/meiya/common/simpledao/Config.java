package com.meiya.common.simpledao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.meiya.common.string.StringUtil;

/**
 * 读取配置，填充至静态属性
 * @author chenc
 *
 */
public class Config {

	private static Logger logger = Logger.getLogger(Config.class);
	private static String configFile = ClassLoader.getSystemResource("")
			.toString().replaceAll("file:/", "")+"config/config.ini";// 配置文件
	
	private static Map<String,String> props=new HashMap<String,String>();

	public static String getProps(String key){
		return StringUtil.isEmpty(props.get(key))?"":props.get(key);
	}
	
	public static String getPropsWithDefault(String key,String defStr){
		return StringUtil.isEmpty(props.get(key))?defStr:props.get(key);
	}
	
	public static Integer getIntegerProps(String key){
		return StringUtil.isEmpty(props.get(key))?0:Integer.valueOf(props.get(key));
	}

	static {
		load();
	}
	
	/**
	 * 从文件中读取属性
	 */
	private static void load(){
		InputStreamReader fis = null;
		try {
			fis = new InputStreamReader(new FileInputStream(configFile),"utf-8");
			Properties dbField = new Properties();
			dbField.load(fis);
			for(Object o : dbField.keySet()){
				props.put(o.toString(), dbField.get(o).toString());
			}

//			driver = dbField.getProperty("driver", "");
//			url = dbField.getProperty("url", "");
//			user = dbField.getProperty("user", "");
//			pwd = dbField.getProperty("password", "");
//			initCon = Integer.parseInt(dbField.getProperty("initcon", "3"));
//			maxCon = Integer.parseInt(dbField.getProperty("maxcon", "10"));
//			incCon = Integer.parseInt(dbField.getProperty("inccon", "3"));
//			overtime = Integer.parseInt(dbField.getProperty("overtime", "60"));
//			sleeptime = Integer.parseInt(dbField.getProperty("sleeptime", "30"));				

		} catch (FileNotFoundException e) {
			logger.error("config/config.ini 配置文件不存在");
		} catch (IOException e) {
			logger.error("读取 config/config.ini 配置文件错误");
		}		
	}
	
	/**
	 * 重新获取属性
	 */
	public static void reload(){
		load();
	}
}
