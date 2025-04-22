package org.eclipse.jgit.api;

import org.eclipse.jgit.storage.file.GC.RepoStatistics;

public class GarbageCollectResult {

	private RepoStatistics preStats;

	private RepoStatistics postStats;

	GarbageCollectResult(RepoStatistics preStats
		this.preStats = preStats;
		this.postStats = postStats;
	}

	public RepoStatistics getPreRepositoryStatistics() {
		return preStats;
	}

	public RepoStatistics getPostRepositoryStatistics() {
		return postStats;
	}

}
