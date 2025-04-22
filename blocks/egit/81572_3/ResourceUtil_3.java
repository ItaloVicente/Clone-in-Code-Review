package org.eclipse.egit.core.internal.indexdiff;

import java.util.function.Supplier;

import org.eclipse.jgit.lib.Repository;

class RepositorySupplier implements Supplier<Repository> {

	private boolean initialized = false;

	private Repository repository;

	private final IndexDiffCacheEntry entry;

	RepositorySupplier(IndexDiffCacheEntry entry) {
		this.entry = entry;
	}

	@Override
	public Repository get() {
		if (!initialized) {
			initialized = true;
			repository = entry.getRepository();
		}
		return repository;
	}

}
