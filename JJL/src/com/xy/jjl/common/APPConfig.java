package com.xy.jjl.common;

public class APPConfig {
	
	//public final static String BaseLink="http://192.168.0.112:8080/";
	//public final static String BaseUrl="http://192.168.0.112:8080/JJLserver";
	
	public final static String BaseLink="http://112.74.38.240:8080/";	
	public final static String BaseUrl="http://112.74.38.240:8080/JJLserver";
	
	public final static String Login=BaseUrl + "/loginServlet";//��½
	
	public final static String uploadImage=BaseUrl + "/imageUploadServlet";//�ϴ�ͼƬ
	
	public final static String Media=BaseUrl + "/mediaServlet";
	
	//key
	public final static String SHARE_PATH = "share_path";
	
	public final static String SETTING = "setting";
	
	public final static String LOGIN = "loginFlag";// ��¼��־���Ƿ��Ѿ���¼��

	public final static String USER_ID =  "userId";// ��¼�ɹ�ʱ�� userId
 
	public final static String ID =  "personalID";// �û���6λ�����
	public final static String USER_NAME =  "userName";// �ʺţ���������¼ʱû�У�
	public final static String PWD =  "password";// ���루��������¼ʱû�У�
 
	public final static String GENDLE =  "gendle";// �Ա�
	//private String age;// ����
	public final static String TEL =  "telephone";// �绰

}
