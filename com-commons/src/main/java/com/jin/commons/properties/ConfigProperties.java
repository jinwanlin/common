package com.jin.commons.properties;

import java.io.InputStream;
import java.util.Properties;


/**
 * 读取config.properties文件
 * 
 * @author jinwanlin
 *
 */
public class ConfigProperties {
	


    /** properties文件 **/
    public static Properties properties = new Properties();
    
	public static void setProperties() {
	}
	

    static {
        try {
            InputStream is = ConfigProperties.class.getResourceAsStream("/config.properties");
            properties.load(is);
            is.close();
        } catch (Exception e) {
        }
        setProperties();
    }
}
