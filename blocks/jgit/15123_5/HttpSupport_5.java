package org.eclipse.jgit.transport.http;

import java.io.IOException;
import java.net.Proxy;
import java.net.URL;

public class JDKHttpConnectionFactory implements HttpConnectionFactory {
	public HttpConnection create(URL url) throws IOException {
		return new JDKHttpConnection(url);
	}

	public HttpConnection create(URL url
			throws IOException {
		return new JDKHttpConnection(url
	}
}
