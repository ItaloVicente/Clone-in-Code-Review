
package org.eclipse.jgit.transport;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.http.HttpConnectionFactory;
import org.eclipse.jgit.transport.http.JDKHttpConnectionFactory;

public abstract class HttpTransport extends Transport {
	protected static HttpConnectionFactory connectionFactory = new JDKHttpConnectionFactory();

	public static HttpConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public static void setConnectionFactory(HttpConnectionFactory cf) {
		connectionFactory = cf;
	}

	protected HttpTransport(Repository local
		super(local
	}

	protected HttpTransport(URIish uri) {
		super(uri);
	}
}
