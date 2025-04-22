
package org.eclipse.jgit.http.server.resolver;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.Publisher;
import org.eclipse.jgit.transport.PublisherClient;
import org.eclipse.jgit.transport.ServiceMayNotContinueException;
import org.eclipse.jgit.transport.resolver.PublisherClientFactory;
import org.eclipse.jgit.transport.resolver.RepositoryResolver;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;

public class DefaultPublisherClientFactory
		implements PublisherClientFactory<HttpServletRequest> {
	private Publisher publisher;

	private RepositoryResolver<HttpServletRequest> resolver;

	public void setResolver(RepositoryResolver<HttpServletRequest> r) {
		this.resolver = r;
	}

	public void setPublisher(Publisher p) {
		this.publisher = p;
	}

	public PublisherClient create(final HttpServletRequest req)
			throws ServiceNotEnabledException
		if (publisher.isClosed())
			throw new ServiceNotEnabledException();
		return new PublisherClient(publisher) {
			@Override
			public Repository openRepository(String name)
					throws RepositoryNotFoundException
					ServiceMayNotContinueException
					ServiceNotAuthorizedException
				try {
					return resolver.open(req
				} catch (ServiceNotEnabledException e) {
					throw new ServiceNotEnabledException(name);
				}
			}
		};
	}
}
