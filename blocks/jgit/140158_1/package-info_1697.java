package org.eclipse.jgit.transport.http;

import java.io.IOException;
import java.net.Proxy;
import java.net.URL;

public class JDKHttpConnectionFactory implements HttpConnectionFactory {
	@Override
	public HttpConnection create(URL url) throws IOException {
		return new JDKHttpConnection(url);
	}

	@Override
	public HttpConnection create(URL url
			throws IOException {
		return new JDKHttpConnection(url
	}
}
