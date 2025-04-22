
package org.eclipse.jgit.transport.resolver;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.UploadPack;

public interface UploadPackFactory<C> {
	UploadPackFactory<?> DISABLED = new UploadPackFactory<Object>() {
		@Override
		public UploadPack create(Object req
				throws ServiceNotEnabledException {
			throw new ServiceNotEnabledException();
		}
	};

	UploadPack create(C req
			ServiceNotAuthorizedException;
}
