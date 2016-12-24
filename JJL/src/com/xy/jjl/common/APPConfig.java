package com.xy.jjl.common;

public class APPConfig {
	
	//public final static String BaseLink="http://192.168.0.112:8080/";
	//public final static String BaseUrl="http://192.168.0.112:8080/JJLserver";
	
	public final static String BaseLink="http://112.74.38.240:8080/";	
	public final static String BaseUrl="http://112.74.38.240:8080/JJLserver";
	
	public final static String Login=BaseUrl + "/loginServlet";//登陆
	
	public final static String uploadImage=BaseUrl + "/imageUploadServlet";//上传图片
	
	public final static String Media=BaseUrl + "/mediaServlet";
	
	//key
	public final static String SHARE_PATH = "share_path";
	
	public final static String SETTING = "setting";
	
	public final static String LOGIN = "loginFlag";// 登录标志（是否已经登录）

	public final static String USER_ID =  "userId";// 登录成功时的 userId
 
	public final static String ID =  "personalID";// 用户的6位随机数
	public final static String USER_NAME =  "userName";// 帐号（第三方登录时没有）
	public final static String PWD =  "password";// 密码（第三方登录时没有）
 
	public final static String GENDLE =  "gendle";// 性别
	//private String age;// 年龄
	public final static String TEL =  "telephone";// 电话

}
