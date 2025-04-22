
package org.eclipse.jgit.http.server.resolver;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.ReceivePack;

public interface ReceivePackFactory {
	public static final ReceivePackFactory DISABLED = new ReceivePackFactory() {
		public ReceivePack create(HttpServletRequest req
				throws ServiceNotEnabledException {
			throw new ServiceNotEnabledException();
		}
	};

	ReceivePack create(HttpServletRequest req
			throws ServiceNotEnabledException;
}
