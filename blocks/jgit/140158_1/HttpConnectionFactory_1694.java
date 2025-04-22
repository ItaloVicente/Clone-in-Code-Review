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
import javax.net.ssl.TrustManager;

import org.eclipse.jgit.annotations.NonNull;

public interface HttpConnection {
	int HTTP_OK = java.net.HttpURLConnection.HTTP_OK;

	int HTTP_MOVED_PERM = java.net.HttpURLConnection.HTTP_MOVED_PERM;

	int HTTP_MOVED_TEMP = java.net.HttpURLConnection.HTTP_MOVED_TEMP;

	int HTTP_SEE_OTHER = java.net.HttpURLConnection.HTTP_SEE_OTHER;

	int HTTP_11_MOVED_TEMP = 307;

	int HTTP_NOT_FOUND = java.net.HttpURLConnection.HTTP_NOT_FOUND;

	int HTTP_UNAUTHORIZED = java.net.HttpURLConnection.HTTP_UNAUTHORIZED;

	int HTTP_FORBIDDEN = java.net.HttpURLConnection.HTTP_FORBIDDEN;

	int getResponseCode() throws IOException;

	URL getURL();

	String getResponseMessage() throws IOException;

	Map<String

	void setRequestProperty(String key

	void setRequestMethod(String method)
			throws ProtocolException;

	void setUseCaches(boolean usecaches);

	void setConnectTimeout(int timeout);

	void setReadTimeout(int timeout);

	String getContentType();

	InputStream getInputStream() throws IOException;

	String getHeaderField(@NonNull String name);

	List<String> getHeaderFields(@NonNull String name);

	int getContentLength();

	void setInstanceFollowRedirects(boolean followRedirects);

	void setDoOutput(boolean dooutput);

	void setFixedLengthStreamingMode(int contentLength);

	OutputStream getOutputStream() throws IOException;

	void setChunkedStreamingMode(int chunklen);

	String getRequestMethod();

	boolean usingProxy();

	void connect() throws IOException;

	void configure(KeyManager[] km
			SecureRandom random) throws NoSuchAlgorithmException
			KeyManagementException;

	void setHostnameVerifier(HostnameVerifier hostnameverifier)
			throws NoSuchAlgorithmException
}
