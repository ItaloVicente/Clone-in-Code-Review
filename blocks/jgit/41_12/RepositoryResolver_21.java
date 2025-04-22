
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

	private final boolean exportAll;

	public FileResolver(final File basePath
		this.basePath = basePath;
		this.exportAll = exportAll;
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
			throw new RepositoryNotFoundException(repositoryName
		}

		try {
			if (isExportOk(req
				return db;
			} else
				throw new ServiceNotEnabledException();

		} catch (RuntimeException e) {
			db.close();
			throw new RepositoryNotFoundException(repositoryName

		} catch (IOException e) {
			db.close();
			throw new RepositoryNotFoundException(repositoryName

		} catch (ServiceNotEnabledException e) {
			db.close();
			throw e;
		}
	}

	protected boolean isExportAll() {
		return exportAll;
	}

	protected boolean isExportOk(HttpServletRequest req
			Repository db) throws IOException {
		if (isExportAll())
			return true;
		else
			return new File(db.getDirectory()
	}

	private static boolean isUnreasonableName(final String name) {
		if (name.length() == 0)

		if (name.indexOf('\\') >= 0)
		if (new File(name).isAbsolute())

		if (name.startsWith("../"))
		if (name.contains("/../"))
		if (name.contains("/./"))

	}
}
