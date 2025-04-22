package org.eclipse.egit.gitflow.op;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.egit.gitflow.GitFlowRepository;
import org.eclipse.egit.gitflow.WrongGitFlowStateException;
import org.eclipse.egit.gitflow.internal.CoreText;

abstract public class AbstractReleaseOperation extends
		AbstractVersionFinishOperation {
	public AbstractReleaseOperation(GitFlowRepository repository,
			String releaseName) {
		super(repository, releaseName);
	}

	protected static String getReleaseName(GitFlowRepository repository)
			throws WrongGitFlowStateException, CoreException, IOException {
		if (!repository.isRelease()) {
			throw new WrongGitFlowStateException(
					CoreText.AbstractReleaseOperation_notOnAReleaseBranch);
		}
		String currentBranch = repository.getRepository().getBranch();
		return currentBranch.substring(repository.getReleasePrefix().length());
	}
}
