package org.eclipse.egit.core;

import org.eclipse.jgit.lib.Repository;

public class ConfigScope implements AutoCloseable {

	private CachingRepository repo;

	public ConfigScope(Repository repository) {
		Repository toCache = repository;
		if (repository instanceof RepositoryHandle) {
			toCache = ((RepositoryHandle) repository).getDelegate();
		}
		if (toCache instanceof CachingRepository) {
			repo = (CachingRepository) toCache;
			repo.cacheConfig(true);
		}
	}

	@Override
	public void close() {
		if (repo != null) {
			repo.cacheConfig(false);
			repo = null;
		}
	}
}
