package main.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class HelloWorldJsonTest {

	private static String Url = "http://localhost:8080/was-runtime/HelloWorldJson.txt";

	private static byte[] makePostData() throws UnsupportedEncodingException, IOException {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("message1", "So good!!");
		param.put("message2", "very good!!");
		param.put("message3", "Awesome!!");

		Gson gson = new Gson();
		String requestJson = gson.toJson(param);

		System.out.println(requestJson);

		return requestJson.getBytes("UTF-8");
	}

	public static void main(String args[]) throws Exception {
		HttpURLConnection conn = null;

		byte[] reqeustByte = makePostData();

		try {
			conn = (HttpURLConnection) new URL(Url).openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("User-Agent", "ETC");
			conn.setRequestProperty("Content-Type", "text/plain");
			conn.setRequestProperty("Connection", "close");
			conn.setConnectTimeout(10 * 1000);
			conn.setReadTimeout(10 * 1000);
			OutputStream out = conn.getOutputStream();
			out.write(reqeustByte, 0, reqeustByte.length);
			out.flush();

			int result = conn.getResponseCode();
			if (result != 200) {
				throw new IOException("Fail to call. code=[" + result + "].\n" + conn.getResponseMessage());
			}

			ByteArrayOutputStream bout = new ByteArrayOutputStream(Math.max(conn.getContentLength(), 100));
			byte[] buffer = new byte[64];
			InputStream resStream = conn.getInputStream();
			int readLen = -1;
			while ((readLen = resStream.read(buffer)) != -1) {
				bout.write(buffer, 0, readLen);
			}

			System.out.println(new String(bout.toString()));

		} finally {
			conn.disconnect();
		}

	}

}
