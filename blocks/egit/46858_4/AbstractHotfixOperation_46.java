package org.eclipse.egit.gitflow.op;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.egit.gitflow.GitFlowRepository;
import org.eclipse.egit.gitflow.WrongGitFlowStateException;
import org.eclipse.egit.gitflow.internal.CoreText;

abstract public class AbstractFeatureOperation extends GitFlowOperation {
	protected String featureName;

	public AbstractFeatureOperation(GitFlowRepository repository,
			String featureName) {
		super(repository);
		this.featureName = featureName;
	}

	protected static String getFeatureName(GitFlowRepository repository)
			throws WrongGitFlowStateException, CoreException, IOException {
		if (!repository.isFeature()) {
			throw new WrongGitFlowStateException(
					CoreText.AbstractFeatureOperation_notOnAFeautreBranch);
		}
		String currentBranch = repository.getRepository().getBranch();
		return currentBranch.substring(repository.getFeaturePrefix().length());
	}

}
