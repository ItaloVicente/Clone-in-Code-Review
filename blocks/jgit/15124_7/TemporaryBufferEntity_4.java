package org.eclipse.jgit.transport.http.apache;

import java.io.IOException;
import java.net.Proxy;
import java.net.URL;

import org.eclipse.jgit.transport.http.HttpConnection;
import org.eclipse.jgit.transport.http.HttpConnectionFactory;

public class HttpClientConnectionFactory implements HttpConnectionFactory {
	public HttpConnection create(URL url) throws IOException {
		return new HttpClientConnection(url.toString());
	}

	public HttpConnection create(URL url
			throws IOException {
		return new HttpClientConnection(url.toString()
	}
}
