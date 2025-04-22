package org.eclipse.egit.ui.internal.synchronize.compare;

import static org.eclipse.egit.ui.internal.CompareUtils.getFileCachedRevisionTypedElement;

import org.eclipse.compare.ITypedElement;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.egit.ui.internal.LocalResourceTypedElement;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.team.ui.mapping.ISynchronizationCompareInput;

public class GitLocalCompareInput extends GitCompareInput {

	public GitLocalCompareInput(Repository repo,
			ComparisonDataSource ancestroDataSource,
			ComparisonDataSource baseDataSource,
			ComparisonDataSource remoteDataSource, String gitPath) {
		super(repo, ancestroDataSource, baseDataSource, remoteDataSource,
				gitPath);
	}

	public ITypedElement getLeft() {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		String absoluteFilePath = repo.getWorkTree().getAbsolutePath()
				+ "/" + gitPath; //$NON-NLS-1$
		IFile file = root.getFileForLocation(new Path(absoluteFilePath));

		return new LocalResourceTypedElement(file);
	}

	public ITypedElement getRight() {
		return getFileCachedRevisionTypedElement(gitPath, repo);
	}

}
