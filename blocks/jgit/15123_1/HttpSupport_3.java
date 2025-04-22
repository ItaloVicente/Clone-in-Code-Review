package org.eclipse.jgit.util;

import java.io.IOException;
import java.net.Proxy;
import java.net.URL;

public interface HttpConnectionFactory {
	public HttpConnection create(URL url) throws IOException;

	public HttpConnection create(URL url
			throws IOException;
}
