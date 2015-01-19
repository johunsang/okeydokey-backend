package main.test;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.okeydokey.backend.utils.HexDumpUtil;

public class HelloWorldBinaryTest {

	private static String Url = "http://localhost:8080/was-runtime/HelloWorldBinary.bnry";

	private static byte[] makePostData() throws UnsupportedEncodingException, IOException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		DataOutputStream dout = new DataOutputStream(bout);
		// first
		dout.write("OkeyDOkey".getBytes("UTF-8"));
		// second
		dout.writeInt(100);

		dout.flush();

		System.out.println(HexDumpUtil.toHexDump(bout.toByteArray()));

		return bout.toByteArray();
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
			conn.setRequestProperty("Content-Type", "application/octet-stream");
			conn.setRequestProperty("Connection", "close");
			conn.setConnectTimeout(10 * 1000);
			conn.setReadTimeout(10 * 1000);
			OutputStream out = conn.getOutputStream();
			out.write(reqeustByte, 0, reqeustByte.length);
			out.flush();

			ByteArrayOutputStream bout = new ByteArrayOutputStream(Math.max(conn.getContentLength(), 100));
			byte[] buffer = new byte[2048];
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
