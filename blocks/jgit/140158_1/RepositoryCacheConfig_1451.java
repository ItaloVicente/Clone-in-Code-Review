
package org.eclipse.jgit.lib;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.internal.WorkQueue;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.RawParseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RepositoryCache {
	private final static Logger LOG = LoggerFactory
			.getLogger(RepositoryCache.class);

	private static final RepositoryCache cache = new RepositoryCache();

	public static Repository open(Key location) throws IOException
			RepositoryNotFoundException {
		return open(location
	}

	public static Repository open(Key location
			throws IOException {
		return cache.openRepository(location
	}

	public static void register(Repository db) {
		if (db.getDirectory() != null) {
			FileKey key = FileKey.exact(db.getDirectory()
			cache.registerRepository(key
		}
	}

	public static void close(@NonNull Repository db) {
		if (db.getDirectory() != null) {
			FileKey key = FileKey.exact(db.getDirectory()
			cache.unregisterAndCloseRepository(key);
		}
	}

	public static void unregister(Repository db) {
		if (db.getDirectory() != null) {
			unregister(FileKey.exact(db.getDirectory()
		}
	}

	public static void unregister(Key location) {
		cache.unregisterRepository(location);
	}

	public static Collection<Key> getRegisteredKeys() {
		return cache.getKeys();
	}

	static boolean isCached(@NonNull Repository repo) {
		File gitDir = repo.getDirectory();
		if (gitDir == null) {
			return false;
		}
		FileKey key = new FileKey(gitDir
		return cache.cacheMap.get(key) == repo;
	}

	public static void clear() {
		cache.clearAll();
	}

	static void clearExpired() {
		cache.clearAllExpired();
	}

	static void reconfigure(RepositoryCacheConfig repositoryCacheConfig) {
		cache.configureEviction(repositoryCacheConfig);
	}

	private final ConcurrentHashMap<Key

	private final Lock[] openLocks;

	private ScheduledFuture<?> cleanupTask;

	private volatile long expireAfter;

	private RepositoryCache() {
		cacheMap = new ConcurrentHashMap<>();
		openLocks = new Lock[4];
		for (int i = 0; i < openLocks.length; i++) {
			openLocks[i] = new Lock();
		}
		configureEviction(new RepositoryCacheConfig());
	}

	private void configureEviction(
			RepositoryCacheConfig repositoryCacheConfig) {
		expireAfter = repositoryCacheConfig.getExpireAfter();
		ScheduledThreadPoolExecutor scheduler = WorkQueue.getExecutor();
		synchronized (scheduler) {
			if (cleanupTask != null) {
				cleanupTask.cancel(false);
			}
			long delay = repositoryCacheConfig.getCleanupDelay();
			if (delay == RepositoryCacheConfig.NO_CLEANUP) {
				return;
			}
			cleanupTask = scheduler.scheduleWithFixedDelay(new Runnable() {
				@Override
				public void run() {
					try {
						cache.clearAllExpired();
					} catch (Throwable e) {
						LOG.error(e.getMessage()
					}
				}
			}
		}
	}

	private Repository openRepository(final Key location
			final boolean mustExist) throws IOException {
		Repository db = cacheMap.get(location);
		if (db == null) {
			synchronized (lockFor(location)) {
				db = cacheMap.get(location);
				if (db == null) {
					db = location.open(mustExist);
					cacheMap.put(location
				} else {
					db.incrementOpen();
				}
			}
		} else {
			db.incrementOpen();
		}
		return db;
	}

	private void registerRepository(Key location
		try (Repository oldDb = cacheMap.put(location
		}
	}

	private Repository unregisterRepository(Key location) {
		return cacheMap.remove(location);
	}

	private boolean isExpired(Repository db) {
		return db != null && db.useCnt.get() <= 0
			&& (System.currentTimeMillis() - db.closedAt.get() > expireAfter);
	}

	private void unregisterAndCloseRepository(Key location) {
		synchronized (lockFor(location)) {
			Repository oldDb = unregisterRepository(location);
			if (oldDb != null) {
				oldDb.doClose();
			}
		}
	}

	private Collection<Key> getKeys() {
		return new ArrayList<>(cacheMap.keySet());
	}

	private void clearAllExpired() {
		for (Repository db : cacheMap.values()) {
			if (isExpired(db)) {
				RepositoryCache.close(db);
			}
		}
	}

	private void clearAll() {
		for (Key k : cacheMap.keySet()) {
			unregisterAndCloseRepository(k);
		}
	}

	private Lock lockFor(Key location) {
		return openLocks[(location.hashCode() >>> 1) % openLocks.length];
	}

	private static class Lock {
	}

	public static interface Key {
		Repository open(boolean mustExist) throws IOException
				RepositoryNotFoundException;
	}

	public static class FileKey implements Key {
		public static FileKey exact(File directory
			return new FileKey(directory
		}

		public static FileKey lenient(File directory
			final File gitdir = resolve(directory
			return new FileKey(gitdir != null ? gitdir : directory
		}

		private final File path;
		private final FS fs;

		protected FileKey(File directory
			path = canonical(directory);
			this.fs = fs;
		}

		private static File canonical(File path) {
			try {
				return path.getCanonicalFile();
			} catch (IOException e) {
				return path.getAbsoluteFile();
			}
		}

		public final File getFile() {
			return path;
		}

		@Override
		public Repository open(boolean mustExist) throws IOException {
			if (mustExist && !isGitRepository(path
				throw new RepositoryNotFoundException(path);
			return new FileRepository(path);
		}

		@Override
		public int hashCode() {
			return path.hashCode();
		}

		@Override
		public boolean equals(Object o) {
			return o instanceof FileKey && path.equals(((FileKey) o).path);
		}

		@Override
		public String toString() {
			return path.toString();
		}

		public static boolean isGitRepository(File dir
			return fs.resolve(dir
					&& fs.resolve(dir
					&& isValidHead(new File(dir
		}

		private static boolean isValidHead(File head) {
			final String ref = readFirstLine(head);
			return ref != null
		}

		private static String readFirstLine(File head) {
			try {
				final byte[] buf = IO.readFully(head
				int n = buf.length;
				if (n == 0)
					return null;
				if (buf[n - 1] == '\n')
					n--;
				return RawParseUtils.decode(buf
			} catch (IOException e) {
				return null;
			}
		}

		public static File resolve(File directory
			if (isGitRepository(directory
				return directory;
			if (isGitRepository(new File(directory
				return new File(directory

			final String name = directory.getName();
			final File parent = directory.getParentFile();
			if (isGitRepository(new File(parent
				return new File(parent
			return null;
		}
	}
}
