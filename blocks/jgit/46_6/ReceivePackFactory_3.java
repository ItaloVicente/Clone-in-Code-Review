
package org.eclipse.jgit.http.server.resolver;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.Config.SectionParser;
import org.eclipse.jgit.transport.ReceivePack;

public class DefaultReceivePackFactory implements ReceivePackFactory {
	private static final SectionParser<ServiceConfig> CONFIG = new SectionParser<ServiceConfig>() {
		public ServiceConfig parse(final Config cfg) {
			return new ServiceConfig(cfg);
		}
	};

	private static class ServiceConfig {
		final boolean set;

		final boolean enabled;

		ServiceConfig(final Config cfg) {
			set = cfg.getString("http"
			enabled = cfg.getBoolean("http"
		}
	}

	public ReceivePack create(final HttpServletRequest req
			throws ServiceNotEnabledException
		final ServiceConfig cfg = db.getConfig().get(CONFIG);
		String user = req.getRemoteUser();

		if (cfg.set) {
			if (cfg.enabled) {
				if (user == null || "".equals(user))
					user = "anonymous";
				return createFor(req
			}
			throw new ServiceNotEnabledException();
		}

		if (user != null && !"".equals(user))
			return createFor(req
		throw new ServiceNotAuthorizedException();
	}

	private ReceivePack createFor(final HttpServletRequest req
			final Repository db
		final ReceivePack rp = new ReceivePack(db);
		rp.setRefLogIdent(toPersonIdent(req
		return rp;
	}

	private static PersonIdent toPersonIdent(HttpServletRequest req
		return new PersonIdent(user
	}
}
