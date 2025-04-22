package org.eclipse.jgit.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.TrustManager;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.eclipse.jgit.util.TemporaryBuffer.LocalFile;

public class HttpClientConnection implements HttpConnection {
	HttpClient client;
	String urlStr;
	HttpUriRequest req;

	HttpResponse resp = null;
	String method = "GET";

	private LocalFile entityBuffer = null;

	private boolean isUsingProxy = false;

	public HttpClientConnection(String urlStr) {
		this(urlStr
	}

	public HttpClientConnection(String urlStr
		this(urlStr
	}

	public HttpClientConnection(String urlStr
		this.client = cl;
		this.urlStr = urlStr;
		if (proxy != null && !Proxy.NO_PROXY.equals(proxy)) {
			isUsingProxy = true;
			InetSocketAddress adr = (InetSocketAddress) proxy.address();
			client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY
					new HttpHost(adr.getHostName()
		}
	}

	public int getResponseCode() throws IOException {
		execute();
		return resp.getStatusLine().getStatusCode();
	}

	public URL getURL() {
		try {
			return new URL(urlStr);
		} catch (MalformedURLException e) {
			return null;
		}
	}

	public String getResponseMessage() throws IOException {
		execute();
		return resp.getStatusLine().getReasonPhrase();
	}

	private void execute() throws IOException
		if (resp == null) {
			if (entityBuffer != null)
				if (req instanceof HttpEntityEnclosingRequest) {
					HttpEntityEnclosingRequest eReq = (HttpEntityEnclosingRequest) req;
					eReq.setEntity(new InputStreamEntity(entityBuffer
							.openInputStream()
				}
			resp = client.execute(req);
			entityBuffer = null;
		}
	}

	public Map<String
		Map<String
		for (Header hdr : resp.getAllHeaders()) {
			List<String> list = new LinkedList<String>();
			for (HeaderElement hdrElem : hdr.getElements())
				list.add(hdrElem.toString());
			ret.put(hdr.getName()
		}
		return ret;
	}

	public void setRequestProperty(String name
		req.addHeader(name
	}

	public void setRequestMethod(String method) throws ProtocolException {
		this.method = method;
			req = new HttpGet(urlStr);
			req = new HttpPut(urlStr);
			req = new HttpPost(urlStr);
		else {
			this.method = null;
			throw new UnsupportedOperationException();
		}
	}

	public void setUseCaches(boolean usecaches) {
	}

	public void setConnectTimeout(int timeout) {
		client.getParams().setIntParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT
	}

	public void setReadTimeout(int timeout) {
		client.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT
				timeout);
	}

	public String getContentType() {
		HttpEntity entity = resp.getEntity();
		if (entity != null) {
			Header contentType = entity.getContentType();
			if (contentType != null)
				return contentType.getValue();
		}
		return null;
	}

	public InputStream getInputStream() throws IOException {
		return resp.getEntity().getContent();
	}

	public String getHeaderField(String name) {
		Header header = resp.getFirstHeader(name);
		return (header == null) ? null : header.getValue();
	}

	public int getContentLength() {
		return Integer.parseInt(resp.getFirstHeader("content-length")
				.getValue());
	}

	public void setInstanceFollowRedirects(boolean followRedirects) {
		client.getParams().setBooleanParameter(ClientPNames.HANDLE_REDIRECTS
				followRedirects);
	}

	public void setDoOutput(boolean dooutput) {
	}

	public void setFixedLengthStreamingMode(int contentLength) {
	}

	public OutputStream getOutputStream() throws IOException {
		if (entityBuffer == null)
			entityBuffer = new TemporaryBuffer.LocalFile();
		return entityBuffer;
	}

	public void setChunkedStreamingMode(int chunklen) {
		throw new UnsupportedOperationException();
	}

	public String getRequestMethod() {
		return method;
	}

	public void disconnect() {
		throw new UnsupportedOperationException();
	}

	public boolean usingProxy() {
		return isUsingProxy;
	}

	public void connect() throws IOException {
		execute();
	}

	public void setHostnameVerifier(HostnameVerifier hostnameverifier) {
	}

	public void configure(KeyManager[] km
			SecureRandom random) {

	}
}
