
package org.eclipse.jgit.internal.ketch;

import java.net.URISyntaxException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.jgit.internal.storage.dfs.DfsRepository;
import org.eclipse.jgit.lib.Repository;

public class KetchLeaderCache {
	private final KetchSystem system;
	private final ConcurrentMap<String
	private final Lock startLock;

	public KetchLeaderCache(KetchSystem system) {
		this.system = system;
		leaders = new ConcurrentHashMap<>();
	}

	public KetchLeader get(Repository repo)
			throws URISyntaxException {
		String key = computeKey(repo);
		KetchLeader leader = leaders.get(key);
		if (leader != null) {
			return leader;
		}
		return startLeader(key
	}

	private KetchLeader startLeader(String key
			throws URISyntaxException {
		startLock.lock();
		try {
			KetchLeader leader = leaders.get(key);
			if (leader != null) {
				return leader;
			}
			leader = system.createLeader(repo);
			leaders.put(key
			return leader;
		} finally {
			startLock.unlock();
		}
	}

	private static String computeKey(Repository repo) {
		if (repo instanceof DfsRepository) {
			DfsRepository dfs = (DfsRepository) repo;
			return dfs.getDescription().getRepositoryName();
		}

		if (repo.getDirectory() != null) {
			return repo.getDirectory().toURI().toString();
		}

		throw new IllegalArgumentException();
	}
}
