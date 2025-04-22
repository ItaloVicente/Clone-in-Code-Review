package org.eclipse.egit.gitflow.op;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egit.gitflow.GitFlowRepository;
import org.eclipse.egit.gitflow.WrongGitFlowStateException;
import org.eclipse.egit.gitflow.internal.CoreText;

public final class ReleaseFinishOperation extends AbstractReleaseOperation {
	public ReleaseFinishOperation(GitFlowRepository repository,
			String releaseName) {
		super(repository, releaseName);
	}

	public ReleaseFinishOperation(GitFlowRepository repository)
			throws WrongGitFlowStateException, CoreException, IOException {
		this(repository, getReleaseName(repository));
	}

	@Override
	public void execute(IProgressMonitor monitor) throws CoreException {
		String releaseBranchName = repository.getConfig().getReleaseBranchName(versionName);
		mergeTo(monitor, releaseBranchName, repository.getConfig().getMaster());
		finish(monitor, releaseBranchName);
		safeCreateTag(monitor, repository.getConfig().getVersionTagPrefix() + versionName,
				CoreText.ReleaseFinishOperation_releaseOf + versionName);
	}
}
