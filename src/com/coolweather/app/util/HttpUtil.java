package com.coolweather.app.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {
	public static void sendHttpRequest(final String address,final HttpCallbackListener listener) {
		new Thread(new Runnable() {
			HttpURLConnection connection = null;

			@Override
			public void run() {
				try {
					URL url = new URL(address);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setReadTimeout(8000);
					connection.setReadTimeout(8000);
					InputStream in = connection.getInputStream();
					BufferedReader br = new BufferedReader(
							new InputStreamReader(in));
					StringBuilder responses = new StringBuilder();
					String line;
					while ((line = br.readLine()) != null) {
						responses.append(line);
					}
					if (listener != null) {
						listener.onFinish(responses.toString());
					}
				} catch (Exception e) {
					if (listener != null) {
						listener.onError(e);
					}
				} finally {
					if (connection != null) {
						connection.disconnect();
					}
				}

			}
		}).start();
	}
}
