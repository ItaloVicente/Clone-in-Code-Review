package org.eclipse.egit.ui.internal.synchronize.compare;

import static org.eclipse.egit.ui.internal.CompareUtils.getIndexTypedElement;
import static org.eclipse.egit.ui.internal.CompareUtils.getFileRevisionTypedElement;

import java.io.IOException;

import org.eclipse.compare.ITypedElement;
import org.eclipse.core.resources.IFile;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.team.ui.mapping.ISynchronizationCompareInput;

public class GitCacheCompareInput extends GitCompareInput {

	private final IFile baseFile;

	public GitCacheCompareInput(Repository repo,
			IFile baseFile, ComparisonDataSource ancestorDataSource,
			ComparisonDataSource baseDataSource,
			ComparisonDataSource remoteDataSource, String gitPath) {
		super(repo, ancestorDataSource, baseDataSource, remoteDataSource,
				gitPath);
		this.baseFile = baseFile;
	}

	@Override
	public ITypedElement getLeft() {
		try {
			return getIndexTypedElement(baseFile);
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public ITypedElement getRight() {
		return getFileRevisionTypedElement(gitPath, baseCommit, repo);
	}

	@Override
	public ITypedElement getAncestor() {
		return getRight();
	}

}
