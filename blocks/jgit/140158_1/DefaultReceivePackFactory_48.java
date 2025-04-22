
package org.eclipse.jgit.http.server.resolver;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;

public class AsIsFileService {
	public static final AsIsFileService DISABLED = new AsIsFileService() {
		@Override
		public void access(HttpServletRequest req
				throws ServiceNotEnabledException {
			throw new ServiceNotEnabledException();
		}
	};

	private static class ServiceConfig {
		final boolean enabled;

		ServiceConfig(Config cfg) {
			enabled = cfg.getBoolean("http"
		}
	}

	protected static boolean isEnabled(Repository db) {
		return db.getConfig().get(ServiceConfig::new).enabled;
	}

	public void access(HttpServletRequest req
			throws ServiceNotEnabledException
		if (!isEnabled(db))
			throw new ServiceNotEnabledException();
	}
}
