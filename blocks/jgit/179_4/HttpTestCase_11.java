
package org.eclipse.jgit.http.test.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.jetty.http.security.Constraint;
import org.eclipse.jetty.http.security.Password;
import org.eclipse.jetty.security.Authenticator;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.MappedLoginService;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jgit.transport.URIish;

public class AppServer {
	public static final String realm = "Secure Area";

	public static final String username = "agitter";

	public static final String password = "letmein";

	static {
		final String prop = "org.eclipse.jetty.util.log.class";
		System.setProperty(prop
	}

	private final Server server;

	private final Connector connector;

	private final ContextHandlerCollection contexts;

	private final TestRequestLog log;

	public AppServer() {
		connector = new SelectChannelConnector();
		connector.setPort(0);
		try {
			final InetAddress me = InetAddress.getByName("localhost");
			connector.setHost(me.getHostAddress());
		} catch (UnknownHostException e) {
			throw new RuntimeException("Cannot find localhost"
		}

		final QueuedThreadPool pool = new QueuedThreadPool();
		pool.setMinThreads(1);
		pool.setMaxThreads(4);
		pool.setMaxQueued(8);

		contexts = new ContextHandlerCollection();

		log = new TestRequestLog();

		final RequestLogHandler logHandler = new RequestLogHandler();
		logHandler.setHandler(contexts);
		logHandler.setRequestLog(log);

		server = new Server();
		server.setConnectors(new Connector[] { connector });
		server.setThreadPool(pool);
		server.setHandler(logHandler);

		server.setStopAtShutdown(false);
		server.setGracefulShutdown(0);
	}

	public ServletContextHandler addContext(String path) {
		assertNotYetSetUp();
		if ("".equals(path))
			path = "/";

		ServletContextHandler ctx = new ServletContextHandler();
		ctx.setContextPath(path);
		contexts.addHandler(ctx);

		return ctx;
	}

	public ServletContextHandler authBasic(ServletContextHandler ctx) {
		assertNotYetSetUp();
		auth(ctx
		return ctx;
	}

	private void auth(ServletContextHandler ctx
		final String role = "can-access";

		MappedLoginService users = new MappedLoginService() {
			@Override
			protected UserIdentity loadUser(String who) {
				return null;
			}

			@Override
			protected void loadUsers() throws IOException {
				putUser(username
			}
		};

		ConstraintMapping cm = new ConstraintMapping();
		cm.setConstraint(new Constraint());
		cm.getConstraint().setAuthenticate(true);
		cm.getConstraint().setDataConstraint(Constraint.DC_NONE);
		cm.getConstraint().setRoles(new String[] { role });

		ConstraintSecurityHandler sec = new ConstraintSecurityHandler();
		sec.setStrict(false);
		sec.setRealmName(realm);
		sec.setAuthenticator(authType);
		sec.setLoginService(users);
		sec.setConstraintMappings(new ConstraintMapping[] { cm });
		sec.setHandler(ctx);

		contexts.removeHandler(ctx);
		contexts.addHandler(sec);
	}

	public void setUp() throws Exception {
		RecordingLogger.clear();
		log.clear();
		server.start();
	}

	public void tearDown() throws Exception {
		RecordingLogger.clear();
		log.clear();
		server.stop();
	}

	public URI getURI() {
		assertAlreadySetUp();
		String host = connector.getHost();
		if (host.contains(":") && !host.startsWith("["))
			host = "[" + host + "]";
		try {
			return new URI(uri);
		} catch (URISyntaxException e) {
			throw new RuntimeException("Unexpected URI error on " + uri
		}
	}

	public int getPort() {
		assertAlreadySetUp();
		return ((SelectChannelConnector) connector).getLocalPort();
	}

	public List<AccessEvent> getRequests() {
		return new ArrayList<AccessEvent>(log.getEvents());
	}

	public List<AccessEvent> getRequests(URIish base
		return getRequests(HttpTestCase.join(base
	}

	public List<AccessEvent> getRequests(String path) {
		ArrayList<AccessEvent> r = new ArrayList<AccessEvent>();
		for (AccessEvent event : log.getEvents()) {
			if (event.getPath().equals(path)) {
				r.add(event);
			}
		}
		return r;
	}

	private void assertNotYetSetUp() {
		Assert.assertFalse("server is not running"
	}

	private void assertAlreadySetUp() {
		Assert.assertTrue("server is running"
	}
}
