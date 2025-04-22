
package org.eclipse.jgit.http.test;

import static org.junit.Assert.fail;

import java.io.IOException;

import javax.servlet.http.HttpServletRequestWrapper;

import org.eclipse.jetty.server.Request;
import org.eclipse.jgit.http.server.resolver.AsIsFileService;
import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;
import org.junit.Before;
import org.junit.Test;

public class AsIsServiceTest extends LocalDiskRepositoryTestCase {
	private Repository db;

	private AsIsFileService service;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		db = createBareRepository();
		service = new AsIsFileService();
	}

	@Test
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

	@Test
	public void testCreate_Default() throws ServiceNotEnabledException
			ServiceNotAuthorizedException {
		service.access(new R(null
		service.access(new R("bob"
	}

	@Test
	public void testCreate_Disabled() throws ServiceNotAuthorizedException
			IOException {
		final StoredConfig cfg = db.getConfig();
		cfg.setBoolean("http"
		cfg.save();

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

	@Test
	public void testCreate_Enabled() throws ServiceNotEnabledException
			ServiceNotAuthorizedException {
		db.getConfig().setBoolean("http"
		service.access(new R(null
		service.access(new R("bob"
	}

	private static final class R extends HttpServletRequestWrapper {
		private final String user;

		private final String host;

		R(String user
			super(new Request(null
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
