
package org.eclipse.jgit.http.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.eclipse.jetty.server.Request;
import org.eclipse.jgit.http.server.resolver.DefaultReceivePackFactory;
import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.ReceivePack;
import org.eclipse.jgit.transport.resolver.ReceivePackFactory;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;
import org.junit.Before;
import org.junit.Test;

public class DefaultReceivePackFactoryTest extends LocalDiskRepositoryTestCase {
	private Repository db;

	private ReceivePackFactory<HttpServletRequest> factory;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		db = createBareRepository();
		factory = new DefaultReceivePackFactory();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDisabledSingleton() throws ServiceNotAuthorizedException {
		factory = (ReceivePackFactory<HttpServletRequest>) ReceivePackFactory.DISABLED;

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
	public void testCreate_NullUser() throws ServiceNotEnabledException {
		try {
			factory.create(new R(null
			fail("Created session for anonymous user: null");
		} catch (ServiceNotAuthorizedException e) {
		}
	}

	@Test
	public void testCreate_EmptyStringUser() throws ServiceNotEnabledException {
		try {
			factory.create(new R(""
			fail("Created session for anonymous user: \"\"");
		} catch (ServiceNotAuthorizedException e) {
		}
	}

	@Test
	public void testCreate_AuthUser() throws ServiceNotEnabledException
			ServiceNotAuthorizedException {
		ReceivePack rp;
		rp = factory.create(new R("bob"
		assertNotNull("have ReceivePack"
		assertSame(db

		PersonIdent id = rp.getRefLogIdent();
		assertNotNull(id);
		assertEquals("bob"
		assertEquals("bob@1.2.3.4"

		assertEquals(author.getTimeZoneOffset()
		assertEquals(author.getWhen()
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
	public void testCreate_Enabled() throws ServiceNotEnabledException
			ServiceNotAuthorizedException
		final StoredConfig cfg = db.getConfig();
		cfg.setBoolean("http"
		cfg.save();

		ReceivePack rp;

		rp = factory.create(new R(null
		assertNotNull("have ReceivePack"
		assertSame(db

		PersonIdent id = rp.getRefLogIdent();
		assertNotNull(id);
		assertEquals("anonymous"
		assertEquals("anonymous@1.2.3.4"

		assertEquals(author.getTimeZoneOffset()
		assertEquals(author.getWhen()

		rp = factory.create(new R("bob"
		assertNotNull("have ReceivePack"
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
