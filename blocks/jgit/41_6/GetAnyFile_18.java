
package org.eclipse.jgit.http.server.resolver;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryCache;
import org.eclipse.jgit.lib.RepositoryCache.FileKey;

public class FileResolver implements RepositoryResolver {
	private final File basePath;

	public FileResolver(final File basePath) {
		this.basePath = basePath;
	}

	public Repository open(final HttpServletRequest req
			final String repositoryName) throws RepositoryNotFoundException
			ServiceNotEnabledException {
		if (isUnreasonableName(repositoryName))
			throw new RepositoryNotFoundException(repositoryName);

		final Repository db;
		try {
			final File gitdir = new File(basePath
			db = RepositoryCache.open(FileKey.lenient(gitdir)
		} catch (IOException e) {
			final RepositoryNotFoundException notFound;
			notFound = new RepositoryNotFoundException(repositoryName);
			notFound.initCause(e);
			throw notFound;
		}

		try {
			if (isExportOk(req
				return db;
			else
				throw new ServiceNotEnabledException();

		} catch (RuntimeException e) {
			db.close();
			final RepositoryNotFoundException notFound;
			notFound = new RepositoryNotFoundException(repositoryName);
			notFound.initCause(e);
			throw notFound;

		} catch (IOException e) {
			db.close();
			final RepositoryNotFoundException notFound;
			notFound = new RepositoryNotFoundException(repositoryName);
			notFound.initCause(e);
			throw notFound;

		} catch (ServiceNotEnabledException e) {
			db.close();
			throw e;
		}
	}

	protected boolean isExportOk(HttpServletRequest req
			Repository db) throws IOException {
		return true;
	}

	private static boolean isUnreasonableName(final String name) {
		if (name.length() == 0)

		if (name.indexOf('\\') >= 0)
		if (name.charAt(0) == '/')
		if (new File(name).isAbsolute())

		if (name.startsWith("../"))
		if (name.contains("/../"))
		if (name.contains("/./"))

	}
}
