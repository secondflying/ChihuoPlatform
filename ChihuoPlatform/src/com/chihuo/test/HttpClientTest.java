package com.chihuo.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HttpClientTest {

	DefaultHttpClient client;
	String rootUrl = "http://localhost:8080/ChihuoPlatform/rest";

	@Before
	public void setUp() throws Exception {
		client = new DefaultHttpClient();
//		HttpHost proxy = new HttpHost("localhost", 8888);
//		client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
	}

	@After
	public void tearDown() throws Exception {
		client.getConnectionManager().shutdown();
	}


	@Test
	public void 登录测试() throws ClientProtocolException, IOException {
		HttpPost post = new HttpPost(rootUrl + "/wlogin");

		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        nvps.add(new BasicNameValuePair("restaurant", "2"));
        nvps.add(new BasicNameValuePair("username", "w1"));
        nvps.add(new BasicNameValuePair("password", "111111"));

        post.setEntity(new UrlEncodedFormEntity(nvps));

		HttpResponse response = client.execute(post);
		
		Assert.assertEquals(200, response.getStatusLine().getStatusCode());

		//判断十分登录成功
		if(200 == response.getStatusLine().getStatusCode()){
			//将返回的auth串保存
			String auth = response.getFirstHeader("Authorization").getValue();
			System.out.println(auth);
		}
	}
	
	@Test
	public void 发送请求() throws ClientProtocolException, IOException {
		HttpGet get = new HttpGet(rootUrl + "/test");
		
		//服务员每次每次发送请求，添加此Header
		String auth = "uid=1,token=2ea6201a068c5fa0eea5d81a3863321a87f8d533";
		get.addHeader("Authorization", auth);
		HttpResponse response = client.execute(get);

		Assert.assertEquals(200, response.getStatusLine().getStatusCode());

		HttpEntity entity = response.getEntity();
		System.out.println(entity.getContentType().getValue());

		String str = showContent(entity.getContent());
		System.out.println(str);
	}
	
	
	private String showContent(InputStream stream) throws IOException {
		StringBuilder sb = new StringBuilder();

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(stream));
		String line = reader.readLine();
		while (line != null) {
			sb.append(line);
			line = reader.readLine();
		}
		reader.close();
		return sb.toString();
	}
	

}
