package org.eclipse.jgit.transport.http;

import java.io.IOException;
import java.net.Proxy;
import java.net.URL;

public interface HttpConnectionFactory {
	HttpConnection create(URL url) throws IOException;

	HttpConnection create(URL url
			throws IOException;
}
