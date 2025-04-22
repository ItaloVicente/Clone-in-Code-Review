
package org.eclipse.jgit.http.test;

import javax.servlet.http.HttpServletRequestWrapper;

import org.eclipse.jetty.server.Request;
import org.eclipse.jgit.http.server.resolver.DefaultReceivePackFactory;
import org.eclipse.jgit.http.server.resolver.ReceivePackFactory;
import org.eclipse.jgit.http.server.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.http.server.resolver.ServiceNotEnabledException;
import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.ReceivePack;

public class DefaultReceivePackFactoryTest extends LocalDiskRepositoryTestCase {
	private Repository db;

	private ReceivePackFactory factory;

	protected void setUp() throws Exception {
		super.setUp();

		db = createBareRepository();
		factory = new DefaultReceivePackFactory();
	}

	public void testDisabledSingleton() throws ServiceNotAuthorizedException {
		factory = ReceivePackFactory.DISABLED;

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

	public void testCreate_NullUser() throws ServiceNotEnabledException {
		try {
			factory.create(new R(null
			fail("Created session for anonymous user: null");
		} catch (ServiceNotAuthorizedException e) {
		}
	}

	public void testCreate_EmptyStringUser() throws ServiceNotEnabledException {
		try {
			factory.create(new R(""
			fail("Created session for anonymous user: \"\"");
		} catch (ServiceNotAuthorizedException e) {
		}
	}

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

	public void testCreate_Disabled() throws ServiceNotAuthorizedException {
		db.getConfig().setBoolean("http"

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

	public void testCreate_Enabled() throws ServiceNotEnabledException
			ServiceNotAuthorizedException {
		db.getConfig().setBoolean("http"
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
