
package org.eclipse.jgit.http.server.resolver;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.ReceivePack;

public interface ReceivePackFactory {
	ReceivePack create(HttpServletRequest req
			throws ServiceNotEnabledException;
}
