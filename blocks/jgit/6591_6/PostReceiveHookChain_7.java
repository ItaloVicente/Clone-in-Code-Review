
package org.eclipse.jgit.http.server.resolver;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jgit.transport.Publisher;
import org.eclipse.jgit.transport.PublisherClient;
import org.eclipse.jgit.transport.resolver.PublisherClientFactory;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;

public class DefaultPublisherClientFactory
		implements PublisherClientFactory<HttpServletRequest> {
	public class PublisherClientConnectionWrapper extends PublisherClient {
		HttpServletRequest req;

		public PublisherClientConnectionWrapper(
				Publisher p
			super(p);
			this.req = req;
		}

		public HttpServletRequest getRequest() {
			return req;
		}
	}

	private Publisher publisher;

	public void setPublisher(Publisher p) {
		this.publisher = p;
	}

	public PublisherClient create(HttpServletRequest req)
			throws ServiceNotEnabledException
		return new PublisherClientConnectionWrapper(publisher
	}
}
