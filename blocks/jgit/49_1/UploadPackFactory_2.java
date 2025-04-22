
package org.eclipse.jgit.http.server.resolver;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.Config.SectionParser;
import org.eclipse.jgit.transport.UploadPack;

public class DefaultUploadPackFactory implements UploadPackFactory {
	private static final SectionParser<ServiceConfig> CONFIG = new SectionParser<ServiceConfig>() {
		public ServiceConfig parse(final Config cfg) {
			return new ServiceConfig(cfg);
		}
	};

	private static class ServiceConfig {
		final boolean enabled;

		ServiceConfig(final Config cfg) {
			enabled = cfg.getBoolean("daemon"
		}
	}

	public UploadPack create(final HttpServletRequest req
			throws ServiceNotEnabledException {
		if (db.getConfig().get(CONFIG).enabled)
			return new UploadPack(db);
		else
			throw new ServiceNotEnabledException();
	}
}
