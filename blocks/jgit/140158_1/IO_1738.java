
package org.eclipse.jgit.util;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.transport.http.HttpConnection;

public class HttpSupport {





























	public static void encode(StringBuilder urlstr
		if (key == null || key.length() == 0)
			return;
		try {
			urlstr.append(URLEncoder.encode(key
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(JGitText.get().couldNotURLEncodeToUTF8
		}
	}

	public static int response(HttpConnection c) throws IOException {
		try {
			return c.getResponseCode();
		} catch (ConnectException ce) {
			final URL url = c.getURL();
				throw new ConnectException(MessageFormat.format(JGitText.get().connectionTimeOut
		}
	}

	public static int response(java.net.HttpURLConnection c)
			throws IOException {
		try {
			return c.getResponseCode();
		} catch (ConnectException ce) {
			final URL url = c.getURL();
				throw new ConnectException(MessageFormat.format(
						JGitText.get().connectionTimeOut
		}
	}

	public static String responseHeader(final HttpConnection c
			final String headerName) throws IOException {
		return c.getHeaderField(headerName);
	}

	public static Proxy proxyFor(ProxySelector proxySelector
			throws ConnectException {
		try {
			return proxySelector.select(u.toURI()).get(0);
		} catch (URISyntaxException e) {
			final ConnectException err;
			err = new ConnectException(MessageFormat.format(JGitText.get().cannotDetermineProxyFor
			err.initCause(e);
			throw err;
		}
	}

	public static void disableSslVerify(HttpConnection conn)
			throws IOException {
		final TrustManager[] trustAllCerts = new TrustManager[] {
				new DummyX509TrustManager() };
		try {
			conn.configure(null
			conn.setHostnameVerifier(new DummyHostnameVerifier());
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			throw new IOException(e.getMessage());
		}
	}

	private static class DummyX509TrustManager implements X509TrustManager {
		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		@Override
		public void checkClientTrusted(X509Certificate[] certs
				String authType) {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] certs
				String authType) {
		}
	}

	private static class DummyHostnameVerifier implements HostnameVerifier {
		@Override
		public boolean verify(String hostname
			return true;
		}
	}

	private HttpSupport() {
	}
}
