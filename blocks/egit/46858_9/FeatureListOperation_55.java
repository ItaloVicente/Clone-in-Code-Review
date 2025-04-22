package org.eclipse.egit.gitflow.op;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egit.gitflow.GitFlowRepository;
import org.eclipse.egit.gitflow.WrongGitFlowStateException;

public final class FeatureFinishOperation extends AbstractFeatureOperation {
	public FeatureFinishOperation(GitFlowRepository repository,
			String featureName) {
		super(repository, featureName);
	}

	public FeatureFinishOperation(GitFlowRepository repository)
			throws CoreException, WrongGitFlowStateException, IOException {
		this(repository, getFeatureName(repository));
	}

	@Override
	public void execute(IProgressMonitor monitor) throws CoreException {
		finish(monitor, repository.getConfig().getFeatureBranchName(featureName));
	}

}
