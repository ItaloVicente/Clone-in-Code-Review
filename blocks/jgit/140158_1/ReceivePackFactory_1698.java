
package org.eclipse.jgit.transport.resolver;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryCache;
import org.eclipse.jgit.lib.RepositoryCache.FileKey;
import org.eclipse.jgit.util.FS;

public class FileResolver<C> implements RepositoryResolver<C> {
	private volatile boolean exportAll;

	private final Map<String

	private final Collection<File> exportBase;

	public FileResolver() {
		exports = new ConcurrentHashMap<>();
		exportBase = new CopyOnWriteArrayList<>();
	}

	public FileResolver(File basePath
		this();
		exportDirectory(basePath);
		setExportAll(exportAll);
	}

	@Override
	public Repository open(C req
			throws RepositoryNotFoundException
		if (isUnreasonableName(name))
			throw new RepositoryNotFoundException(name);

		Repository db = exports.get(nameWithDotGit(name));
		if (db != null) {
			db.incrementOpen();
			return db;
		}

		for (File base : exportBase) {
			File dir = FileKey.resolve(new File(base
			if (dir == null)
				continue;

			try {
				FileKey key = FileKey.exact(dir
				db = RepositoryCache.open(key
			} catch (IOException e) {
				throw new RepositoryNotFoundException(name
			}

			try {
				if (isExportOk(req
					return db;
				} else
					throw new ServiceNotEnabledException();

			} catch (RuntimeException | IOException e) {
				db.close();
				throw new RepositoryNotFoundException(name

			} catch (ServiceNotEnabledException e) {
				db.close();
				throw e;
			}
		}

		if (exportBase.size() == 1) {
			File dir = new File(exportBase.iterator().next()
			throw new RepositoryNotFoundException(name
					new RepositoryNotFoundException(dir));
		}

		throw new RepositoryNotFoundException(name);
	}

	public boolean isExportAll() {
		return exportAll;
	}

	public void setExportAll(boolean export) {
		exportAll = export;
	}

	public void exportRepository(String name
		exports.put(nameWithDotGit(name)
	}

	public void exportDirectory(File dir) {
		exportBase.add(dir);
	}

	protected boolean isExportOk(C req
			throws IOException {
		if (isExportAll())
			return true;
		else if (db.getDirectory() != null)
			return new File(db.getDirectory()
		else
			return false;
	}

	private static String nameWithDotGit(String name) {
		if (name.endsWith(Constants.DOT_GIT_EXT))
			return name;
		return name + Constants.DOT_GIT_EXT;
	}

	private static boolean isUnreasonableName(String name) {
		if (name.length() == 0)

		if (name.indexOf('\\') >= 0)
		if (new File(name).isAbsolute())


	}
}
