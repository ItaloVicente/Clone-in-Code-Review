
package org.eclipse.jgit.http.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.eclipse.jetty.server.Request;
import org.eclipse.jgit.http.server.resolver.DefaultUploadPackFactory;
import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.UploadPack;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;
import org.eclipse.jgit.transport.resolver.UploadPackFactory;
import org.junit.Before;
import org.junit.Test;

public class DefaultUploadPackFactoryTest extends LocalDiskRepositoryTestCase {
	private Repository db;

	private UploadPackFactory<HttpServletRequest> factory;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		db = createBareRepository();
		factory = new DefaultUploadPackFactory();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDisabledSingleton() throws ServiceNotAuthorizedException {
		factory = (UploadPackFactory<HttpServletRequest>) UploadPackFactory.DISABLED;

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

	@Test
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

	@Test
	public void testCreate_Disabled() throws ServiceNotAuthorizedException
			IOException {
		final StoredConfig cfg = db.getConfig();
		cfg.setBoolean("http"
		cfg.save();

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

	@Test
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
