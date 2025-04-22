package org.eclipse.egit.core;

import java.io.IOException;
import java.util.function.Supplier;

import org.eclipse.jgit.lib.Repository;

public final class UnitOfWork {

	private UnitOfWork() {
	}

	@FunctionalInterface
	public interface Work {

		void execute() throws IOException;
	}

	@FunctionalInterface
	public interface WorkWithResult<T extends Object> {

		T get() throws IOException;
	}

	public static void run(Repository repository, Work work)
			throws IOException {
		try (ConfigScope scope = new ConfigScope(repository)) {
			work.execute();
		}
	}

	public static void execute(Repository repository, Runnable work) {
		try (ConfigScope scope = new ConfigScope(repository)) {
			work.run();
		}
	}

	public static <T> T run(Repository repository,
			WorkWithResult<? extends T> work) throws IOException {
		try (ConfigScope scope = new ConfigScope(repository)) {
			return work.get();
		}
	}

	public static <T> T get(Repository repository, Supplier<? extends T> work) {
		try (ConfigScope scope = new ConfigScope(repository)) {
			return work.get();
		}
	}

	private static class ConfigScope implements AutoCloseable {

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

}
