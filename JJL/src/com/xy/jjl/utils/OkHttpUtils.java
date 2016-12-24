package com.xy.jjl.utils;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.List;
import java.util.concurrent.TimeUnit;
/**
 * Description : OkHttp�������ӷ�װ������ 
 * Author : ldong 
 * Date   : 16/1/31 
 */  
 
public class OkHttpUtils {  
  
    private static final String TAG = "OkHttpUtils";  
    
    private static final MediaType MEDIA_TYPE_JPG=MediaType.parse("image/jpg");
    private static OkHttpUtils mInstance;  
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;
  
    private OkHttpUtils() {  
        /** 
         * ����OkHttpClient 
         */  
        mOkHttpClient = new OkHttpClient();  
        /** 
         * �������ӵĳ�ʱʱ�� 
         */  
        mOkHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        /** 
         * ������Ӧ�ĳ�ʱʱ�� 
         */  
        mOkHttpClient.setWriteTimeout(10, TimeUnit.SECONDS);
        /** 
         * ����ĳ�ʱʱ�� 
         */  
        mOkHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
        /** 
         * ����ʹ��Cookie 
         */  
        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        /** 
         * ��ȡ���̵߳�handler 
         */  
        mHandler = new Handler(Looper.getMainLooper());
    }  
  
    /** 
     * ͨ������ģʽ������� 
     * @return OkHttpUtils 
     */  
    private synchronized static OkHttpUtils getmInstance() {  
        if (mInstance == null) {  
            mInstance = new OkHttpUtils();  
        }  
        return mInstance;  
    }  
    
    /** 
     * ����Get���� 
     * @param url  �����url 
     * @param callback  ����ص��ķ��� 
     */  
    private void getRequest(String url, final ResultCallback callback) {  
        final Request request = new Request.Builder()
        							.url(url)
        							.build();
        deliveryResult(callback, request);  
    }  
    
    /** 
     * ����post ���� 
     * @param url �����url 
     * @param callback ����ص��ķ��� 
     * @param params ������� 
     */  
    private void postRequest(String url, final ResultCallback callback, List<Param> params) {
        Request request = buildPostRequest(url, params);  
        deliveryResult(callback, request);  
    }  
    
    /**
     * ����post ����,�ļ��ϴ�
     * @param url
     * @param callback
     * @param files
     * @param fileKeys
     * @throws IOException
     *
     */

	private void postRequest(String url,final ResultCallback callback,
    						 File[] files,String[] fileKeys,
    						 List<Param> params){
    	
    	Request request=buildMultipartFormRequest(url,files,fileKeys,params);
    	deliveryResult(callback,request);
    }

	
    
    /**
     * �����ļ�
     * @param url
     * @param destFileDir �����ļ��洢���ļ���
     * @param callback
     * @throws IOException
     *
     */

	private void download(final String url,
						  final String destFileDir,
						  final ResultCallback callback,
						  List<Param> params){

        FormEncodingBuilder builder = new FormEncodingBuilder();  
        
        for (Param param : params) {  
            builder.add(param.key, param.value);  
        }  
        RequestBody requestBody = builder.build();  
        
    	final Request request=new Request.Builder()
    			.url(url)
    			.post(requestBody)
    			.build();
    	final Call call=mOkHttpClient.newCall(request);
    	call.enqueue(new Callback(){

			@Override
			public void onFailure(Request arg0, IOException e) {
				// TODO Auto-generated method stub
				sendFailCallback(callback, e);
			}

			@Override
			public void onResponse(Response response)  {
				// TODO Auto-generated method stub
				InputStream is=null;
				byte[] buf=new byte[2048];
				int len=0;
				FileOutputStream fos=null;
				try{
					String temp=null;
					temp=response.header("Content-Disposition");
					String[] str=temp.split(";");
					String[] strtemp=str[1].split("=");
					String filename=strtemp[1].replace("\"", "");
					String fullname=destFileDir+filename;
					is=response.body().byteStream();
					int mlen=is.available();
					File file=new File(destFileDir,filename);
					fos=new FileOutputStream(file);
					while((len=is.read(buf))!=-1){
						fos.write(buf,0,len);
					}
					fos.flush();
					//������سɹ������ؾ���·��
					sendSuccessCallBack(callback, file.getAbsolutePath());
					
				}catch(IOException e){
					sendFailCallback(callback, e);
				}finally{
					try{
						if(is!=null) is.close();
					}catch(IOException e){
						
					}
					try{
						if(fos!=null) fos.close();
					}catch(IOException e){
						
					}
					
				}
			}
    		
    		
    	});
		
    }
    
	private String getFileName(String path){
		
		int separatorIndex = path.lastIndexOf("/");
  		return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());

	}
 
    /** 
     * �����������Ļص� 
     * @param callback 
     * @param request 
     */  
    private void deliveryResult(final ResultCallback callback, Request request) {  
  
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override  
            public void onFailure(Request request, final IOException e) {
                sendFailCallback(callback, e);  
            }  
  
            @Override  
            public void onResponse(Response response) throws IOException {  
                try {  
                    String str = response.body().string();  
//                    if (callback.mType == String.class) {
                        sendSuccessCallBack(callback, str);  
//                    } else {
//                        Object object = new JSONObject(str);
//                        sendSuccessCallBack(callback, object);
//                    }
                } catch (final Exception e) {  
                    sendFailCallback(callback, e);
                }  
  
            }  
        });  
    }  
  
    /** 
     * ����ʧ�ܵĻص� 
     * @param callback 
     * @param e 
     */  
    private void sendFailCallback(final ResultCallback callback, final Exception e) {  
        mHandler.post(new Runnable() {  
            @Override  
            public void run() {  
                if (callback != null) {  
                    callback.onFailure(e);  
                }  
            }  
        });  
    }  
  
    /** 
     * ���ͳɹ��ĵ� 
     * @param callback 
     * @param obj 
     */  
    private void sendSuccessCallBack(final ResultCallback callback, final Object obj) {  
        mHandler.post(new Runnable() {  
            @Override  
            public void run() {  
                if (callback != null) {  
                    callback.onSuccess(obj);  
                }  
            }  
        });  
    }  
  
    /** 
     * ����post���� 
     * @param url  ����url 
     * @param params ����Ĳ��� 
     * @return ���� Request 
     */  
    private Request buildPostRequest(String url, List<Param> params) {  
    	
        FormEncodingBuilder builder = new FormEncodingBuilder();  
        
        for (Param param : params) {  
            builder.add(param.key, param.value);  
        }  
        RequestBody requestBody = builder.build();  
        return new Request.Builder()
        				.url(url)
        				.post(requestBody)
        				.build();  
    }  
    
    
  
    /**********************����ӿ�************************/  
  
    /** 
     * get���� 
     * @param url  ����url 
     * @param callback  ����ص� 
     */  
    public static void get(String url, ResultCallback callback) {  
        getmInstance().getRequest(url, callback);  
    }  
  
    /** 
     * post���� 
     * @param url       ����url 
     * @param callback  ����ص� 
     * @param params    ������� 
     */  
    public static void post(String url, final ResultCallback callback, List<Param> params) {  
        getmInstance().postRequest(url, callback, params);  
    }  

    /** 
     * 
     * @param url     
     * @param files   
     * @param fileKeys    
     * @param params
     */ 
    public static void uploadImage(String url,final ResultCallback callback,
			 File[] files,String[] fileKeys,
			 List<Param> params){
    	
    	getmInstance().postRequest(url, callback, files, fileKeys, params);
    }
    
    
    public static void downloadImge(final String url,
								    final String destFileDir,
								    final ResultCallback callback,
								    List<Param> params){
    	getmInstance().download(url, destFileDir, callback,params);
    }
    /** 
     * 
     * @param url     
     * @param files   
     * @param fileKeys    
     * @param params
     */ 

    //*********************************************
    
    private Request buildMultipartFormRequest(String url,File[] files,
    										  String[] fileKeys,List<Param> params){
    	MultipartBuilder builder=new MultipartBuilder()
    			.type(MultipartBuilder.FORM);
    			
    	
    	//������
    	for(Param param : params){
    		builder.addPart(Headers.of("Content-Disposition","form-data; name=\"" + param.key + "\""),
    				RequestBody.create(null, param.value));
    	}
    	
    	//���ļ�
    	if(files!=null){
    		RequestBody fileBody=null;
    		for(int i=0;i<files.length;i++){
    			File file=files[i];
    			String fileName=file.getName();
    			fileBody=RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
    			//TODO �����ļ�������contentType
    			builder.addPart(Headers.of("Content-Disposition",
    					"form-data; name=\"" + fileKeys[i] + "\"; filename=\"" + fileName + "\""),
    					fileBody);
    					
    		}
    	}
    	
    	RequestBody requestBody=builder.build();
    	return new Request.Builder()
    			.url(url)
    			.post(requestBody)
    			.build();
    }
    
    private String guessMimeType(String path){
    	FileNameMap fileNameMap=URLConnection.getFileNameMap();
    	String contentTypeFor=fileNameMap.getContentTypeFor(path);
    	if(contentTypeFor==null){
    		contentTypeFor="application/octet-stream";
    	}
    	return contentTypeFor;
    }
    
    /** 
     * http����ص���,�ص�������UI�߳���ִ�� 
     * @param <T> 
     */  
    public static abstract class ResultCallback<T> {  
  
        Type mType;  
  
        public ResultCallback(){  
            mType = getSuperclassTypeParameter(getClass());
        }  
  
        static Type getSuperclassTypeParameter(Class<?> subclass) {  
            Type superclass = subclass.getGenericSuperclass();//���ظ��������  
            if (superclass instanceof Class) {  
                throw new RuntimeException("Missing type parameter.");  
            }  
            ParameterizedType parameterized = (ParameterizedType) superclass;  
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);  
        }  
  
        /** 
         * ����ɹ��ص� 
         * @param response 
         */  
        public abstract void onSuccess(T response);  
  
        /** 
         * ����ʧ�ܻص� 
         * @param e 
         */  
        public abstract void onFailure(Exception e);  
    }  
  
    /** 
     * post��������� 
     */  
    public static class Param {  
  
        String key;//����Ĳ���  
        String value;//������ֵ  
  
        public Param() {  
        }  
  
        public Param(String key, String value) {  
            this.key = key;  
            this.value = value;  
        }  
  
    }  
    
    public static void uploadImage(){
    	
    }
  
  
}  