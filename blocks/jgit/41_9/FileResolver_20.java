
package org.eclipse.jgit.http.server.resolver;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jgit.http.server.GitServlet;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.Config.SectionParser;

public class AsIsFileService {
	public static final AsIsFileService DISABLED = new AsIsFileService() {
		@Override
		public void access(HttpServletRequest req
				throws ServiceNotEnabledException {
			throw new ServiceNotEnabledException();
		}
	};

	private static final SectionParser<ServiceConfig> CONFIG = new SectionParser<ServiceConfig>() {
		public ServiceConfig parse(final Config cfg) {
			return new ServiceConfig(cfg);
		}
	};

	private static class ServiceConfig {
		final boolean enabled;

		ServiceConfig(final Config cfg) {
			enabled = cfg.getBoolean("http"
		}
	}

	protected static boolean isEnabled(Repository db) {
		return db.getConfig().get(CONFIG).enabled;
	}

	public void access(HttpServletRequest req
			throws ServiceNotEnabledException
		if (!isEnabled(db))
			throw new ServiceNotEnabledException();
	}
}
