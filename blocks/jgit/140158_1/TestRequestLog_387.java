package org.eclipse.jgit.junit.http;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.http.server.GitServlet;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.resolver.RepositoryResolver;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;

public class SimpleHttpServer {

	AppServer server;

	private final Repository db;

	private URIish uri;

	private URIish secureUri;

	public SimpleHttpServer(Repository repository) {
		this(repository
	}

	public SimpleHttpServer(Repository repository
		this.db = repository;
		server = new AppServer(0
	}

	public void start() throws Exception {
		ServletContextHandler sBasic = server.authBasic(smart("/sbasic"));
		server.setUp();
		final String srcName = db.getDirectory().getName();
		uri = toURIish(sBasic
		int sslPort = server.getSecurePort();
		if (sslPort > 0) {
			secureUri = uri.setPort(sslPort).setScheme("https");
		}
	}

	public void stop() throws Exception {
		server.tearDown();
	}

	public URIish getUri() {
		return uri;
	}

	public URIish getSecureUri() {
		return secureUri;
	}

	private ServletContextHandler smart(String path) {
		GitServlet gs = new GitServlet();
		gs.setRepositoryResolver(new RepositoryResolver<HttpServletRequest>() {
			@Override
			public Repository open(HttpServletRequest req
					throws RepositoryNotFoundException
					ServiceNotEnabledException {
				if (!name.equals(nameOf(db)))
					throw new RepositoryNotFoundException(name);

				db.incrementOpen();
				return db;
			}
		});

		ServletContextHandler ctx = server.addContext(path);
		ctx.addServlet(new ServletHolder(gs)
		return ctx;
	}

	private static String nameOf(Repository db) {
		return db.getDirectory().getName();
	}

	private URIish toURIish(String path) throws URISyntaxException {
		URI u = server.getURI().resolve(path);
		return new URIish(u.toString());
	}

	private URIish toURIish(ServletContextHandler app
			throws URISyntaxException {
		String p = app.getContextPath();
		if (!p.endsWith("/") && !name.startsWith("/"))
			p += "/";
		p += name;
		return toURIish(p);
	}
}
