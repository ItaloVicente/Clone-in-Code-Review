package org.eclipse.jgit.transport.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

public interface HttpConnection {
	public static final int HTTP_OK = java.net.HttpURLConnection.HTTP_OK;

	public static final int HTTP_NOT_FOUND = java.net.HttpURLConnection.HTTP_NOT_FOUND;

	public static final int HTTP_UNAUTHORIZED = java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

	public static final int HTTP_FORBIDDEN = java.net.HttpURLConnection.HTTP_FORBIDDEN;

	public int getResponseCode() throws IOException;

	public URL getURL();

	public String getResponseMessage() throws IOException;

	public Map<String

	public void setRequestProperty(String key

	public void setRequestMethod(String method)
			throws ProtocolException;

	public void setUseCaches(boolean usecaches);

	public void setConnectTimeout(int timeout);

	public void setReadTimeout(int timeout);

	public String getContentType();

	public InputStream getInputStream() throws IOException;

	public String getHeaderField(String name);

	public int getContentLength();

	public void setInstanceFollowRedirects(boolean followRedirects);

	public void setDoOutput(boolean dooutput);

	public void setFixedLengthStreamingMode(int contentLength);

	public OutputStream getOutputStream() throws IOException;

	public void setChunkedStreamingMode(int chunklen);

	public String getRequestMethod();

	public void disconnect();

	public boolean usingProxy();

	public void connect() throws IOException;

	public void configure(KeyManager[] km
			SecureRandom random) throws NoSuchAlgorithmException
			KeyManagementException;

	public void setHostnameVerifier(HostnameVerifier hostnameverifier)
			throws NoSuchAlgorithmException
}
