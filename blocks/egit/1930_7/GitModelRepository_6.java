package org.eclipse.egit.ui.internal.synchronize.model;

import java.io.IOException;

import org.eclipse.egit.ui.internal.synchronize.compare.ComparisonDataSource;
import org.eclipse.egit.ui.internal.synchronize.compare.GitCacheCompareInput;
import org.eclipse.egit.ui.internal.synchronize.compare.GitCompareInput;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;

class GitModelCacheFile extends GitModelBlob {

	public GitModelCacheFile(GitModelObjectContainer parent, RevCommit commit,
			ObjectId repoId, ObjectId cacheId, String name) throws IOException {
		super(parent, commit, repoId, repoId, cacheId, name);
	}

	@Override
	protected GitCompareInput getCompareInput(ComparisonDataSource baseData,
			ComparisonDataSource remoteData, ComparisonDataSource ancestorData) {
		return new GitCacheCompareInput(getRepository(), ancestorData,
				baseData, remoteData, gitPath);
	}
}
