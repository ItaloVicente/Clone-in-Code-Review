
package org.eclipse.jgit.http.test;

import javax.servlet.http.HttpServletRequestWrapper;

import org.eclipse.jetty.server.Request;
import org.eclipse.jgit.http.server.resolver.DefaultUploadPackFactory;
import org.eclipse.jgit.http.server.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.http.server.resolver.ServiceNotEnabledException;
import org.eclipse.jgit.http.server.resolver.UploadPackFactory;
import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.UploadPack;

public class DefaultUploadPackFactoryTest extends LocalDiskRepositoryTestCase {
	private Repository db;

	private UploadPackFactory factory;

	protected void setUp() throws Exception {
		super.setUp();

		db = createBareRepository();
		factory = new DefaultUploadPackFactory();
	}

	public void testDisabledSingleton() throws ServiceNotAuthorizedException {
		factory = UploadPackFactory.DISABLED;

		try {
			factory.create(new R(null
			fail("Created session for anonymous user: null");
		} catch (ServiceNotEnabledException e) {
		}

		try {
			factory.create(new R(""
			fail("Created session for anonymous user: \"\"");
		} catch (ServiceNotEnabledException e) {
		}

		try {
			factory.create(new R("bob"
			fail("Created session for user: \"bob\"");
		} catch (ServiceNotEnabledException e) {
		}
	}

	public void testCreate_Default() throws ServiceNotEnabledException
			ServiceNotAuthorizedException {
		UploadPack up;

		up = factory.create(new R(null
		assertNotNull("have UploadPack"
		assertSame(db

		up = factory.create(new R("bob"
		assertNotNull("have UploadPack"
		assertSame(db
	}

	public void testCreate_Disabled() throws ServiceNotAuthorizedException {
		db.getConfig().setBoolean("http"

		try {
			factory.create(new R(null
			fail("Created session for anonymous user: null");
		} catch (ServiceNotEnabledException e) {
		}

		try {
			factory.create(new R("bob"
			fail("Created session for user: \"bob\"");
		} catch (ServiceNotEnabledException e) {
		}
	}

	public void testCreate_Enabled() throws ServiceNotEnabledException
			ServiceNotAuthorizedException {
		db.getConfig().setBoolean("http"
		UploadPack up;

		up = factory.create(new R(null
		assertNotNull("have UploadPack"
		assertSame(db

		up = factory.create(new R("bob"
		assertNotNull("have UploadPack"
		assertSame(db
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
