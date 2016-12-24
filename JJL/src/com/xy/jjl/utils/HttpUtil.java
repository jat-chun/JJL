package com.xy.jjl.utils;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class HttpUtil {
	
	//112.74.38.240
	//public final static String BaseUrl="http://112.74.38.240:8080/JJLserver";
	public final static String BaseUrl="http://192.168.0.112:8080/JJLserver";
	
	public final static String Login=BaseUrl 
			+ "/loginServlet";//��½
	
/*`
	//��ʽ���� 115.28.168.64
	//public final static String BaseUrl = "http://115.28.168.64:8080/wcXphServer";
	//public final static String BaseUrl = "http://10.0.0.8:8080/wcXphServer";
	public final static String Register = BaseUrl + "/registerServlet";// ע��
	public final static String Login = BaseUrl + "/loginServlet";// ��¼
	public final static String QQLogin = BaseUrl + "/QQLoginServlet";// qq��¼
	public final static String Apply = BaseUrl + "/applyServlet";// ������ѯ
	public final static String ApplyQueryPrivance = BaseUrl
			+ "/applyQueryProvinceServlet"; // ��ѯȫ��ʡ��
	public final static String ApplyQueryCity = BaseUrl
			+ "/applyQueryCityServlet";// ��ѯʡ�� ����
	public final static String ApplyQueryCityMessage = BaseUrl
			+ "/applyQueryCityMessageServlet";// ��ѯ���ж�Ӧ�Ĳ���������Ϣ
	public final static String ChangePassword = BaseUrl
			+ "/changPasswordServlet";// �޸�����
	public final static String SelectCardMember = BaseUrl
			+ "/selectCardMemberServlet";// ��ѯ�Ƿ�Ϊ���岿���Ա
	public final static String SelectCardMemberInfo = BaseUrl
			+ "/selectCardMemberInfoServlet";// ��ѯ���岿���Ա��Ϣ
	public final static String UpToCardMember = BaseUrl
			+ "/upToCardMemberServlet";// ����Ϊ���岿���Ա
	public final static String TakeMoneyBusiness = BaseUrl
			+ "/takeMoneyBusinessServlet";// �����ѧԺ
	public final static String Types = BaseUrl + "/typeServlet";//
	public final static String TakeMoneySkill = BaseUrl
			+ "/takeMoneySkillServlet";// ���ÿ�������ѵ����
	public final static String CardTribe = BaseUrl + "/cardTribeServlet";
	public final static String TakeMoneyServer = BaseUrl
			+ "/takeMoneyServerServlet";// ����������
	public final static String TakeMoneyBook = BaseUrl
			+ "/takeMoneyBookServlet";// ����
	// �鿴ģ����Ϣ
	public final static String SelectMoban = BaseUrl + "/selectMobanServlet";
	// �޸�ģ�����
	public final static String ChangeMobanBiShu = BaseUrl
			+ "/changMobanBishuServlet";
	// ������ÿ���Ϣ
	public final static String AddCreditCardBill = BaseUrl
			+ "/addCreditCardBillServlet";
	// ����userid��ѯ���п����˵���Ϣ
	public final static String GetCreditCardBillMeessage = BaseUrl
			+ "/getCreditCardBillMessageServlet";
	// ��ѯ�û������ÿ�
	public final static String GetCreditCard = BaseUrl
			+ "/getCreditCardServlet";

	// ���� D�˵���Ϣ
	public final static String GetBillDMessageServlet = BaseUrl
			+ "/getBillDhuanMessageServlet";
	// ��ʾ D�˵���Ϣ
	public final static String ShowBillDMessage = BaseUrl
			+ "/showBillMessageServlet";
	// ��ʾ Y�˵���Ϣ
	public final static String ShowBillYMessage = BaseUrl
			+ "/showBillYMessageServlet";
	// ���� Y�˵���Ϣ
	public final static String GetBillYMessageServlet = BaseUrl
			+ "/getBillYangkaMessageServlet";
	// ���� D�˵� �Ļ���״̬
	public final static String UpdateDhuanStatus = BaseUrl
			+ "/updateDhuanStateServlet";
	// ���� Y�˵��Ļ���״̬
	public final static String UpdateYStatus = BaseUrl
			+ "/updateYangStateServlet";

	// �ύ˲ʱ�� ��Ϣ
	public final static String ImmediatelyLoanMessageCommit = BaseUrl
			+ "/AddSSDServlet";
	// �ύ��˾�� ��Ϣ
	public final static String CorporationLoanMessageCommit = BaseUrl
			+ "/AddGSDServlet";

	// ���������� ����apk �İ汾��
	public final static String CheckServerCode = BaseUrl
			+ "/checkVersionServlet";
	// �����°汾apk
	public final static String UploadVersion = BaseUrl
			+ "/UploadVersionServlet";
	// ɾ������Ϣ
	public final static String DeleteCardMessage = BaseUrl
			+ "/DelctCreditCardServlet";
	// ���������ȡ�˵�
	public final static String LoadEmailBill = BaseUrl + "/LoadEmailServlet";
	// �����û�id ��ȡ�û������б�
	public final static String ShowUserEmail = BaseUrl
			+ "/ShowUserEmailServlet";
	// �����û�id ��ȡ�û����ÿ���Ϣ�б�
	public final static String ShowUserCreditCardMessage = BaseUrl
			+ "/ShowCreditCradServlet";
	// �����û�id�������ַɾ������
	public final static String DeleteUserEmail = BaseUrl
			+ "/DeleteEmailServlet";
	// �������ÿ�id ɾ�����ÿ��˵�
	public final static String DeleteCreditCardtBill = BaseUrl
			+ "/DeleteCreditCardServlet";
	// �������ÿ�id ��ȡ�������ÿ��˵�
	public final static String QueryBillsMessage = BaseUrl
			+ "/QueryBillsTradeServlet";
	// ��ѯ���ٰ���Ϣ
	public final static String QueryHonorRollMessage = BaseUrl
			+ "/QueryGRBServlet";
	// ��ѯ���ǽ��Ϣ
	public final static String QueryAdvertisementMessage = BaseUrl
			+ "/QueryGGQServlet";
	// �޸���֤�� ״̬
	public final static String UpdateSecurityCode = BaseUrl
			+ "/UpdateKeyTypeServlet";
	// ��ѯ�Ƿ��Ѿ�����
	public final static String QueryIsActivation = BaseUrl
			+ "/QueryActivationIsUserIdServlet";
	// ��ѯ�Ƽ�������
	public final static String QueryReferrer = BaseUrl
			+ "/QueryReferrerServlet";
	// ��QQ
	public final static String BindingQQ = BaseUrl + "/BindingQQServlet";
	// �����˵� ����״̬ ����Ϊ�ѻ� δ����
	public final static String UpdateRepaymentState = BaseUrl
			+ "/UpdateRepaymentStateServlet";
	// ��ѯ�������Ϣ
	public final static String KazudaiSelect = BaseUrl
			+ "/KazudaiSelectServlet";
	// ��ӿ����������Ϣ
	public final static String KazudaiAddPersonal = BaseUrl
			+ "/KazudaiAddPersonalServlet";
	// ��ӿ������˾��Ϣ
	public final static String KazudaiAddCompany = BaseUrl
			+ "/KazudaiAddCompanyServlet";
	// ��ӿ�����ʽ�����
	public final static String KazudaiFundDemand = BaseUrl
			+ "/KazudaiFundDemandServlet";
	// ��ӿ����ס������
	public final static String KazudaiAddHousing = BaseUrl
			+ "/KazudaiAddHousingServlet";
	// ��ѯ�����ס������
	public final static String KazudaiSelectHouse = BaseUrl
			+ "/KazudaiSelectHouseServlet";
	// ɾ�������ס������
	public final static String KazudaiDelHouse = BaseUrl
			+ "/KazudaiDelHouseServlet";
	// ��ӿ������������
	public final static String KazudaiAddCar = BaseUrl
			+ "/KazudaiAddCarServlet";
	// ��ѯ�������������
	public final static String KazudaiSelectCar = BaseUrl
			+ "/KazudaiSelectCarServlet";
	// ��ѯ����Ӷ��
	public final static String ObtainBroerage = BaseUrl
			+ "/ObtainBroerageServlet";
	// ��ѯ�Ӽ��û���Ϣ
	public final static String ObtainChildMember = BaseUrl
			+ "/ObtainChildMemberServlet";
	// ��ѯ��������
	public final static String QueryBankName = BaseUrl
			+ "/QueryBankNameServlet";
	// ���Ӷ��������Ϣ
	public final static String BroerageDrawingsAdd = BaseUrl
			+ "/BroerageDrawingsAddServlet";
	// ��ѯӶ��������Ϣ
	public final static String BroerageDrawingsSelect = BaseUrl
			+ "/BroerageDrawingsSelectServlet";
	// �õ��Ƽ���id
	public final static String GetReferrerMessage = BaseUrl
			+ "/GetReferrerMessageServlet";
	// ��ѯ���а�
	public final static String Ranklist = BaseUrl
			+ "/RanklistServlet";
	// ��ѯ���������Ϣ
	public final static String QueryBankDrawing = BaseUrl
			+ "/QueryBankDrawingServlet";
	// ���΢������Ϣ
	public final static String AddWSQ = BaseUrl
			+ "/AddWSQServlet";
	// ��ѯ΢������Ϣ
	public final static String QueryWSQ = BaseUrl
			+ "/QueryWSQServlet";
*/
	
	// �Լ�Ҫά��sessionid
	private static String sessionId;

	public static String httpGet(String url) throws Exception {

		HttpGet httpGet = new HttpGet(url);
		// �����ͻ���
		DefaultHttpClient client = HttpClientSingle.getHttpClient();
		if (sessionId != null) {
			httpGet.setHeader("Cookie", "JSESSIONID=" + sessionId);
		}
		HttpResponse response = client.execute(httpGet);
		// �жϷ����Ƿ���ȷ
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			// ���������ص�����
			String result = EntityUtils.toString(response.getEntity(), "UTF-8")
					.trim();
			// ���session��������id
			CookieStore cookieStore = client.getCookieStore();
			List<Cookie> cookies = cookieStore.getCookies();
			for (int i = 0; i < cookies.size(); i++) {
				Cookie cookie = cookies.get(i);
				if (cookie.getName().equals("JSESSIONID")) {
					sessionId = cookie.getValue();
				}
			}
			return result;
		}
		return null;
	}

	public static String httpPost(String url, List<NameValuePair> nameValuePairs)
			throws Exception {
		HttpPost post = new HttpPost(url);
		HttpEntity entity = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
		post.setEntity(entity);

		// �����ͻ���
		DefaultHttpClient client = HttpClientSingle.getHttpClient();
		if (sessionId != null) {
			post.setHeader("Cookie", "JSESSIONID=" + sessionId);
		}
		HttpResponse response = client.execute(post);
		// �жϷ����Ƿ���ȷ
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			// ���������ص�����
			String result = EntityUtils.toString(response.getEntity(), "UTF-8")
					.trim();

			// ���session��������id
			CookieStore cookieStore = client.getCookieStore();
			List<Cookie> cookies = cookieStore.getCookies();
			for (int i = 0; i < cookies.size(); i++) {
				Cookie cookie = cookies.get(i);
				if (cookie.getName().equals("JSESSIONID")) {
					sessionId = cookie.getValue();
				}
			}
			return result;
		}
		return null;
	}

	public static Bitmap down(String urladdress) {
		try {
			URL url = new URL(urladdress);
			URLConnection connection = url.openConnection();
			BufferedInputStream bis = new BufferedInputStream(
					connection.getInputStream());
			Bitmap bitmap = BitmapFactory.decodeStream(bis);
			return bitmap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
