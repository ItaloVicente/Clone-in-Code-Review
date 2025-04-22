
package org.eclipse.jgit.http.server.resolver;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.UploadPack;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;
import org.eclipse.jgit.transport.resolver.UploadPackFactory;

public class DefaultUploadPackFactory implements
		UploadPackFactory<HttpServletRequest> {
	private static class ServiceConfig {
		final boolean enabled;

		ServiceConfig(Config cfg) {
			enabled = cfg.getBoolean("http"
		}
	}

	@Override
	public UploadPack create(HttpServletRequest req
			throws ServiceNotEnabledException
		if (db.getConfig().get(ServiceConfig::new).enabled) {
			UploadPack up = new UploadPack(db);
			if (header != null) {
				up.setExtraParameters(Arrays.asList(params));
			}
			return up;
		} else {
			throw new ServiceNotEnabledException();
		}
	}
}
