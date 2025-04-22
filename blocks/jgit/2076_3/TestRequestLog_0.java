package org.eclipse.jgit.junit.http;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.http.server.GitServlet;
import org.eclipse.jgit.http.server.resolver.RepositoryResolver;
import org.eclipse.jgit.http.server.resolver.ServiceNotEnabledException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.transport.URIish;

public class SimpleHttpServer {

	AppServer server;

	private final FileRepository db;

	private URIish uri;

	public SimpleHttpServer(FileRepository repository) {
		this.db = repository;
		server = new AppServer();
	}

	public void start() throws Exception {
		ServletContextHandler sBasic = server.authBasic(smart("/sbasic"));
		server.setUp();
		final String srcName = db.getDirectory().getName();
		uri = toURIish(sBasic
	}

	public void stop() throws Exception {
		server.tearDown();
	}

	public URIish getUri() {
		return uri;
	}

	private ServletContextHandler smart(final String path) {
		GitServlet gs = new GitServlet();
		gs.setRepositoryResolver(new RepositoryResolver() {
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

	private static String nameOf(final FileRepository db) {
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
