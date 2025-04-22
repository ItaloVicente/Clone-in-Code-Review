package org.eclipse.jgit.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

public class JDKHttpConnection implements HttpConnection {
	HttpURLConnection wrappedUrlConnection;

	protected JDKHttpConnection(URL url)
			throws MalformedURLException
			IOException {
		this.wrappedUrlConnection = (HttpURLConnection) url.openConnection();
	}

	protected JDKHttpConnection(URL url
			throws MalformedURLException
		this.wrappedUrlConnection = (HttpURLConnection) url
				.openConnection(proxy);
	}

	public int getResponseCode() throws IOException {
		return wrappedUrlConnection.getResponseCode();
	}

	public URL getURL() {
		return wrappedUrlConnection.getURL();
	}

	public String getResponseMessage() throws IOException {
		return wrappedUrlConnection.getResponseMessage();
	}

	public Map<String
		return wrappedUrlConnection.getHeaderFields();
	}

	public void setRequestProperty(String key
		wrappedUrlConnection.setRequestProperty(key
	}

	public void setRequestMethod(String method) throws ProtocolException {
		wrappedUrlConnection.setRequestMethod(method);
	}

	public void setUseCaches(boolean usecaches) {
		wrappedUrlConnection.setUseCaches(usecaches);
	}

	public void setConnectTimeout(int timeout) {
		wrappedUrlConnection.setConnectTimeout(timeout);
	}

	public void setReadTimeout(int timeout) {
		wrappedUrlConnection.setReadTimeout(timeout);
	}

	public String getContentType() {
		return wrappedUrlConnection.getContentType();
	}

	public InputStream getInputStream() throws IOException {
		return wrappedUrlConnection.getInputStream();
	}

	public String getHeaderField(String name) {
		return wrappedUrlConnection.getHeaderField(name);
	}

	public int getContentLength() {
		return wrappedUrlConnection.getContentLength();
	}

	public void setInstanceFollowRedirects(boolean followRedirects) {
		wrappedUrlConnection.setInstanceFollowRedirects(followRedirects);
	}

	public void setDoOutput(boolean dooutput) {
		wrappedUrlConnection.setDoOutput(dooutput);
	}

	public void setFixedLengthStreamingMode(int contentLength) {
		wrappedUrlConnection.setFixedLengthStreamingMode(contentLength);
	}

	public OutputStream getOutputStream() throws IOException {
		return wrappedUrlConnection.getOutputStream();
	}

	public void setChunkedStreamingMode(int chunklen) {
		wrappedUrlConnection.setChunkedStreamingMode(chunklen);
	}

	public String getRequestMethod() {
		return wrappedUrlConnection.getRequestMethod();
	}

	public void disconnect() {
		wrappedUrlConnection.disconnect();
	}

	public boolean usingProxy() {
		return wrappedUrlConnection.usingProxy();
	}

	public void connect() throws IOException {
		wrappedUrlConnection.connect();
	}

	public void setHostnameVerifier(HostnameVerifier hostnameverifier) {
		((HttpsURLConnection) wrappedUrlConnection)
				.setHostnameVerifier(hostnameverifier);
	}

	public void configure(KeyManager[] km
			SecureRandom random) throws NoSuchAlgorithmException
			KeyManagementException {
		ctx.init(km
		((HttpsURLConnection) wrappedUrlConnection).setSSLSocketFactory(ctx
				.getSocketFactory());
	}
}
