package org.eclipse.egit.ui.internal.synchronize.compare;

import static org.eclipse.egit.ui.internal.CompareUtils.getFileCachedRevisionTypedElement;
import static org.eclipse.egit.ui.internal.CompareUtils.getFileRevisionTypedElement;

import org.eclipse.compare.ITypedElement;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.team.ui.mapping.ISynchronizationCompareInput;

public class GitCacheCompareInput extends GitCompareInput {

	public GitCacheCompareInput(Repository repo,
			ComparisonDataSource ancestorDataSource,
			ComparisonDataSource baseDataSource,
			ComparisonDataSource remoteDataSource, String gitPath) {
		super(repo, ancestorDataSource, baseDataSource, remoteDataSource,
				gitPath);
	}

	public ITypedElement getLeft() {
		return getFileCachedRevisionTypedElement(gitPath, repo);
	}

	public ITypedElement getRight() {
		return getFileRevisionTypedElement(gitPath, baseCommit, repo);
	}

}
