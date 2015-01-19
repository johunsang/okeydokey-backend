package main.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HelloWorldTextTest {

	private static String Url = "http://localhost:8080/was-runtime/HelloWorldText.txt";

	private byte[] makePostData() throws UnsupportedEncodingException, IOException {
		String requestFlatMessage = "HI ? ";
		System.out.println(requestFlatMessage);
		return requestFlatMessage.getBytes("UTF-8");
	}

	public void callServer(String url, int timeoutInSecond) throws Exception {
		HttpURLConnection conn = null;

		byte[] reqeustByte = makePostData();

		try {
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("User-Agent", "ETC");
			conn.setRequestProperty("Content-Type", "text/plain");
			conn.setRequestProperty("Connection", "close");
			conn.setConnectTimeout(timeoutInSecond * 1000);
			conn.setReadTimeout(timeoutInSecond * 1000);
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

	public static void main(String args[]) throws Exception {
		HelloWorldTextTest test = new HelloWorldTextTest();
		test.callServer(Url, 30);
	}

}
