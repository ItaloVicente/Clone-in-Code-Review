package org.eclipse.jgit.niofs.internal.manager;

import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import org.eclipse.jgit.niofs.internal.JGitFileSystem;
import org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration;
import org.eclipse.jgit.niofs.internal.JGitFileSystemProxy;

public class JGitFileSystemsCache {

	final Map<String

	final Map<String

	public JGitFileSystemsCache(JGitFileSystemProviderConfiguration config) {

		memoizedSuppliers = JGitFileSystemsCacheDataStructure.create(config);
	}

	public void addSupplier(String fsKey
		checkNotNull("fsKey"
		checkNotNull("fsSupplier"

		fileSystemsSuppliers.putIfAbsent(fsKey

		createMemoizedSupplier(fsKey
	}

	public void remove(String fsName) {
		fileSystemsSuppliers.remove(fsName);
		memoizedSuppliers.remove(fsName);
	}

	public JGitFileSystem get(String fsName) {

		Supplier<JGitFileSystem> memoizedSupplier = memoizedSuppliers.get(fsName);
		if (memoizedSupplier != null) {
			return new JGitFileSystemProxy(fsName
		} else if (fileSystemsSuppliers.get(fsName) != null) {
			Supplier<JGitFileSystem> newMemoizedSupplier = createMemoizedSupplier(fsName
					fileSystemsSuppliers.get(fsName));
			return new JGitFileSystemProxy(fsName
		}
		return null;
	}

	private Supplier<JGitFileSystem> createMemoizedSupplier(String fsKey
		Supplier<JGitFileSystem> memoizedFSSupplier = MemoizedFileSystemsSupplier.of(createFSSupplier);
		memoizedSuppliers.putIfAbsent(fsKey
		return memoizedFSSupplier;
	}

	public void clear() {
		memoizedSuppliers.clear();
		fileSystemsSuppliers.clear();
	}

	public boolean containsKey(String fsName) {
		return fileSystemsSuppliers.containsKey(fsName);
	}

	public Collection<String> getFileSystems() {
		return fileSystemsSuppliers.keySet();
	}

	public JGitFileSystemsCacheInfo getCacheInfo() {
		return new JGitFileSystemsCacheInfo();
	}

	public class JGitFileSystemsCacheInfo {

		public int fileSystemsCacheSize() {
			return memoizedSuppliers.size();
		}

		public Set<String> memoizedFileSystemsCacheKeys() {
			return memoizedSuppliers.keySet();
		}

		@Override
		public String toString() {
			return "JGitFileSystemsCacheInfo{fileSystemsCacheSize[" + fileSystemsCacheSize()
					+ "]
		}
	}
}
