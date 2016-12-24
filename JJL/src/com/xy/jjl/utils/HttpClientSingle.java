package com.xy.jjl.utils;

import org.apache.http.HttpVersion;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

public class HttpClientSingle {

	private static DefaultHttpClient httpClient;

	private HttpClientSingle() {

	}

	public synchronized static DefaultHttpClient getHttpClient() {
		if (httpClient == null) {

			// ���ò���
			HttpParams params = new BasicHttpParams();
			// ���ӳ�ʱ
			HttpConnectionParams.setConnectionTimeout(params, 60000);
			// ����ʱ
			HttpConnectionParams.setSoTimeout(params, 60000);

			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);

			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
			
			// ֧��http ��https����
			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			schReg.register(new Scheme("https", PlainSocketFactory
					.getSocketFactory(), 443));

			// ����һ���̰߳�ȫ��HttpClient
			ClientConnectionManager clientConnectionManager = new ThreadSafeClientConnManager(
					params, schReg);

			httpClient = new DefaultHttpClient(clientConnectionManager, params);
		}

		return httpClient;

	}

}