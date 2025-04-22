
package org.eclipse.jgit.transport.resolver;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.ReceivePack;

public interface ReceivePackFactory<C> {
	ReceivePackFactory<?> DISABLED = new ReceivePackFactory<Object>() {
		@Override
		public ReceivePack create(Object req
				throws ServiceNotEnabledException {
			throw new ServiceNotEnabledException();
		}
	};

	ReceivePack create(C req
			ServiceNotAuthorizedException;
}
