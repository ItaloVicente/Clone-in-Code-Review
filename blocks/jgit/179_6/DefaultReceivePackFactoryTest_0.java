
package org.eclipse.jgit.http.test;

import javax.servlet.http.HttpServletRequestWrapper;

import org.eclipse.jetty.server.Request;
import org.eclipse.jgit.http.server.resolver.AsIsFileService;
import org.eclipse.jgit.http.server.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.http.server.resolver.ServiceNotEnabledException;
import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.lib.Repository;

public class AsIsServiceTest extends LocalDiskRepositoryTestCase {
	private Repository db;

	private AsIsFileService service;

	protected void setUp() throws Exception {
		super.setUp();

		db = createBareRepository();
		service = new AsIsFileService();
	}

	public void testDisabledSingleton() throws ServiceNotAuthorizedException {
		service = AsIsFileService.DISABLED;
		try {
			service.access(new R(null
			fail("Created session for anonymous user: null");
		} catch (ServiceNotEnabledException e) {
		}

		try {
			service.access(new R("bob"
			fail("Created session for user: \"bob\"");
		} catch (ServiceNotEnabledException e) {
		}
	}

	public void testCreate_Default() throws ServiceNotEnabledException
			ServiceNotAuthorizedException {
		service.access(new R(null
		service.access(new R("bob"
	}

	public void testCreate_Disabled() throws ServiceNotAuthorizedException {
		db.getConfig().setBoolean("http"

		try {
			service.access(new R(null
			fail("Created session for anonymous user: null");
		} catch (ServiceNotEnabledException e) {
		}

		try {
			service.access(new R("bob"
			fail("Created session for user: \"bob\"");
		} catch (ServiceNotEnabledException e) {
		}
	}

	public void testCreate_Enabled() throws ServiceNotEnabledException
			ServiceNotAuthorizedException {
		db.getConfig().setBoolean("http"
		service.access(new R(null
		service.access(new R("bob"
	}

	private final class R extends HttpServletRequestWrapper {
		private final String user;

		private final String host;

		R(final String user
			this.user = user;
			this.host = host;
		}

		@Override
		public String getRemoteHost() {
			return host;
		}

		@Override
		public String getRemoteUser() {
			return user;
		}
	}
}
