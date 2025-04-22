
package org.eclipse.jgit.transport.resolver;

import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.ServiceMayNotContinueException;

public interface RepositoryResolver<C> {
	RepositoryResolver<?> NONE = new RepositoryResolver<Object>() {
		@Override
		public Repository open(Object req
				throws RepositoryNotFoundException {
			throw new RepositoryNotFoundException(name);
		}
	};

	Repository open(C req
			ServiceNotAuthorizedException
			ServiceMayNotContinueException;
}
