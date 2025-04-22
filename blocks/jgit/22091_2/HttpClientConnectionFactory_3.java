package org.eclipse.jgit.transport.http.apache;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
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
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.transport.http.HttpConnection;
import org.eclipse.jgit.util.TemporaryBuffer;
import org.eclipse.jgit.util.TemporaryBuffer.LocalFile;

public class HttpClientConnection implements HttpConnection {
	HttpClient client;

	String urlStr;

	HttpUriRequest req;

	HttpResponse resp = null;


	private TemporaryBufferEntity entity;

	private boolean isUsingProxy = false;

	private Proxy proxy;

	private Integer timeout = null;

	private Integer readTimeout;

	private Boolean followRedirects;

	private X509HostnameVerifier hostnameverifier;

	SSLContext ctx;

	private HttpClient getClient() {
		if (client == null)
			client = new DefaultHttpClient();
		HttpParams params = client.getParams();
		if (proxy != null && !Proxy.NO_PROXY.equals(proxy)) {
			isUsingProxy = true;
			InetSocketAddress adr = (InetSocketAddress) proxy.address();
			params.setParameter(ConnRoutePNames.DEFAULT_PROXY
					new HttpHost(adr.getHostName()
		}
		if (timeout != null)
			params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT
					timeout.intValue());
		if (readTimeout != null)
			params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT
					readTimeout.intValue());
		if (followRedirects != null)
			params.setBooleanParameter(ClientPNames.HANDLE_REDIRECTS
					followRedirects.booleanValue());
		if (hostnameverifier != null) {
			SSLSocketFactory sf;
			sf = new SSLSocketFactory(getSSLContext()
			Scheme https = new Scheme("https"
			client.getConnectionManager().getSchemeRegistry().register(https);
		}

		return client;
	}

	private SSLContext getSSLContext() {
		if (ctx == null) {
			try {
			} catch (NoSuchAlgorithmException e) {
				throw new IllegalStateException(
						JGitText.get().unexpectedSSLContextException
			}
		}
		return ctx;
	}

	public void setBuffer(TemporaryBuffer buffer) {
		this.entity = new TemporaryBufferEntity(buffer);
	}

	public HttpClientConnection(String urlStr) {
		this(urlStr
	}

	public HttpClientConnection(String urlStr
		this(urlStr
	}

	public HttpClientConnection(String urlStr
		this.client = cl;
		this.urlStr = urlStr;
		this.proxy = proxy;
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
		if (resp == null)
			if (entity != null) {
				if (req instanceof HttpEntityEnclosingRequest) {
					HttpEntityEnclosingRequest eReq = (HttpEntityEnclosingRequest) req;
					eReq.setEntity(entity);
				}
				resp = getClient().execute(req);
				entity.getBuffer().close();
				entity = null;
			} else
				resp = getClient().execute(req);
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
		this.timeout = new Integer(timeout);
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = new Integer(readTimeout);
	}

	public String getContentType() {
		HttpEntity responseEntity = resp.getEntity();
		if (responseEntity != null) {
			Header contentType = responseEntity.getContentType();
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
				.getValue());
	}

	public void setInstanceFollowRedirects(boolean followRedirects) {
		this.followRedirects = new Boolean(followRedirects);
	}

	public void setDoOutput(boolean dooutput) {
	}

	public void setFixedLengthStreamingMode(int contentLength) {
		if (entity != null)
			throw new IllegalArgumentException();
		entity = new TemporaryBufferEntity(new LocalFile());
		entity.setContentLength(contentLength);
	}

	public OutputStream getOutputStream() throws IOException {
		if (entity == null)
			entity = new TemporaryBufferEntity(new LocalFile());
		return entity.getBuffer();
	}

	public void setChunkedStreamingMode(int chunklen) {
		if (entity == null)
			entity = new TemporaryBufferEntity(new LocalFile());
		entity.setChunked(true);
	}

	public String getRequestMethod() {
		return method;
	}

	public boolean usingProxy() {
		return isUsingProxy;
	}

	public void connect() throws IOException {
		execute();
	}

	public void setHostnameVerifier(final HostnameVerifier hostnameverifier) {
		this.hostnameverifier = new X509HostnameVerifier() {
			public boolean verify(String hostname
				return hostnameverifier.verify(hostname
			}

			public void verify(String host
					throws SSLException {
			}

			public void verify(String host
					throws SSLException {
			}

			public void verify(String host
				hostnameverifier.verify(host
			}
		};
	}

	public void configure(KeyManager[] km
			SecureRandom random) throws KeyManagementException {
		getSSLContext().init(km
	}
}
