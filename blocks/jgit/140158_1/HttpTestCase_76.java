
package org.eclipse.jgit.junit.http;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.security.AbstractLoginService;
import org.eclipse.jetty.security.Authenticator;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.security.Constraint;
import org.eclipse.jetty.util.security.Password;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jgit.transport.URIish;

public class AppServer {
	public static final String realm = "Secure Area";

	public static final String username = "agitter";

	public static final String password = "letmein";

	private static final String keyPassword = "mykeys";

	private static final String authRole = "can-access";

	static {
		final String prop = "org.eclipse.jetty.util.log.class";
		System.setProperty(prop
	}

	private final Server server;

	private final HttpConfiguration config;

	private final ServerConnector connector;

	private final HttpConfiguration secureConfig;

	private final ServerConnector secureConnector;

	private final ContextHandlerCollection contexts;

	private final TestRequestLog log;

	private List<File> filesToDelete = new ArrayList<>();

	public AppServer() {
		this(0
	}

	public AppServer(int port) {
		this(port
	}

	public AppServer(int port
		server = new Server();

		config = new HttpConfiguration();
		config.setSecureScheme("https");
		config.setSecurePort(0);
		config.setOutputBufferSize(32768);

		connector = new ServerConnector(server
				new HttpConnectionFactory(config));
		connector.setPort(port);
		String ip;
		String hostName;
		try {
			final InetAddress me = InetAddress.getByName("localhost");
			ip = me.getHostAddress();
			connector.setHost(ip);
			hostName = InetAddress.getLocalHost().getCanonicalHostName();
		} catch (UnknownHostException e) {
			throw new RuntimeException("Cannot find localhost"
		}

		if (sslPort >= 0) {
			SslContextFactory sslContextFactory = createTestSslContextFactory(
					hostName);
			secureConfig = new HttpConfiguration(config);
			secureConnector = new ServerConnector(server
					new SslConnectionFactory(sslContextFactory
							HttpVersion.HTTP_1_1.asString())
					new HttpConnectionFactory(secureConfig));
			secureConnector.setPort(sslPort);
			secureConnector.setHost(ip);
		} else {
			secureConfig = null;
			secureConnector = null;
		}

		contexts = new ContextHandlerCollection();

		log = new TestRequestLog();
		log.setHandler(contexts);

		if (secureConnector == null) {
			server.setConnectors(new Connector[] { connector });
		} else {
			server.setConnectors(
					new Connector[] { connector
		}
		server.setHandler(log);
	}

	private SslContextFactory createTestSslContextFactory(String hostName) {
		SslContextFactory factory = new SslContextFactory(true);

		String dName = "CN=

		try {
			File tmpDir = Files.createTempDirectory("jks").toFile();
			tmpDir.deleteOnExit();
			makePrivate(tmpDir);
			File keyStore = new File(tmpDir
			Runtime.getRuntime().exec(
					new String[] {
							"keytool"
							"-keystore"
							"-storepass"
							"-alias"
							"-genkeypair"
							"-keyalg"
							"-keypass"
							"-dname"
							"-validity"
					}).waitFor();
			keyStore.deleteOnExit();
			makePrivate(keyStore);
			filesToDelete.add(keyStore);
			filesToDelete.add(tmpDir);
			factory.setKeyStorePath(keyStore.getAbsolutePath());
			factory.setKeyStorePassword(keyPassword);
			factory.setKeyManagerPassword(keyPassword);
			factory.setTrustStorePath(keyStore.getAbsolutePath());
			factory.setTrustStorePassword(keyPassword);
		} catch (InterruptedException | IOException e) {
			throw new RuntimeException("Cannot create ssl key/certificate"
		}
		return factory;
	}

	private void makePrivate(File file) {
		file.setReadable(false);
		file.setWritable(false);
		file.setExecutable(false);
		file.setReadable(true
		file.setWritable(true
		if (file.isDirectory()) {
			file.setExecutable(true
		}
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

	public ServletContextHandler authBasic(ServletContextHandler ctx
			String... methods) {
		assertNotYetSetUp();
		auth(ctx
		return ctx;
	}

	static class TestMappedLoginService extends AbstractLoginService {
		private String role;

		protected final ConcurrentMap<String

		TestMappedLoginService(String role) {
			this.role = role;
		}

		@Override
		protected void doStart() throws Exception {
			UserPrincipal p = new UserPrincipal(username
					new Password(password));
			users.put(username
			super.doStart();
		}

		@Override
		protected String[] loadRoleInfo(UserPrincipal user) {
			if (users.get(user.getName()) == null)
				return null;
			else
				return new String[] { role };
		}

		@Override
		protected UserPrincipal loadUserInfo(String user) {
			return users.get(user);
		}
	}

	private ConstraintMapping createConstraintMapping() {
		ConstraintMapping cm = new ConstraintMapping();
		cm.setConstraint(new Constraint());
		cm.getConstraint().setAuthenticate(true);
		cm.getConstraint().setDataConstraint(Constraint.DC_NONE);
		cm.getConstraint().setRoles(new String[] { authRole });
		return cm;
	}

	private void auth(ServletContextHandler ctx
			String... methods) {
		AbstractLoginService users = new TestMappedLoginService(authRole);
		List<ConstraintMapping> mappings = new ArrayList<>();
		if (methods == null || methods.length == 0) {
			mappings.add(createConstraintMapping());
		} else {
			for (String method : methods) {
				ConstraintMapping cm = createConstraintMapping();
				cm.setMethod(method.toUpperCase(Locale.ROOT));
				mappings.add(cm);
			}
		}

		ConstraintSecurityHandler sec = new ConstraintSecurityHandler();
		sec.setRealmName(realm);
		sec.setAuthenticator(authType);
		sec.setLoginService(users);
		sec.setConstraintMappings(
				mappings.toArray(new ConstraintMapping[0]));
		sec.setHandler(ctx);

		contexts.removeHandler(ctx);
		contexts.addHandler(sec);
	}

	public void setUp() throws Exception {
		RecordingLogger.clear();
		log.clear();
		server.start();
		config.setSecurePort(getSecurePort());
		if (secureConfig != null) {
			secureConfig.setSecurePort(getSecurePort());
		}
	}

	public void tearDown() throws Exception {
		RecordingLogger.clear();
		log.clear();
		server.stop();
		for (File f : filesToDelete) {
			f.delete();
		}
		filesToDelete.clear();
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
		return connector.getLocalPort();
	}

	public int getSecurePort() {
		assertAlreadySetUp();
		return secureConnector != null ? secureConnector.getLocalPort() : -1;
	}

	public List<AccessEvent> getRequests() {
		return new ArrayList<>(log.getEvents());
	}

	public List<AccessEvent> getRequests(URIish base
		return getRequests(HttpTestCase.join(base
	}

	public List<AccessEvent> getRequests(String path) {
		ArrayList<AccessEvent> r = new ArrayList<>();
		for (AccessEvent event : log.getEvents()) {
			if (event.getPath().equals(path)) {
				r.add(event);
			}
		}
		return r;
	}

	private void assertNotYetSetUp() {
		assertFalse("server is not running"
	}

	private void assertAlreadySetUp() {
		assertTrue("server is running"
	}
}
