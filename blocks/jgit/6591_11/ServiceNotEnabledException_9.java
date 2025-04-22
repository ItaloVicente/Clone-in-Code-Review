
package org.eclipse.jgit.transport.resolver;

import org.eclipse.jgit.transport.Publisher;
import org.eclipse.jgit.transport.PublisherClient;

public interface PublisherClientFactory<C> {
	public static final PublisherClientFactory DISABLED =
		new PublisherClientFactory() {

		public void setPublisher(Publisher p) {
		}

		public void setResolver(RepositoryResolver r) {
		}

		public PublisherClient create(Object req)
				throws ServiceNotEnabledException
				ServiceNotAuthorizedException {
			throw new ServiceNotEnabledException();
		}
	};

	void setPublisher(Publisher p);

	void setResolver(RepositoryResolver<C> r);

	PublisherClient create(C req)
			throws ServiceNotEnabledException
}
