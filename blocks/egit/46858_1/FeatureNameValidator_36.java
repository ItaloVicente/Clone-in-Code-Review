package org.eclipse.egit.gitflow.ui.internal.validation;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.egit.gitflow.BranchNameValidator;
import org.eclipse.egit.gitflow.GitFlowRepository;

public class FeatureNameValidator extends NameValidator {
	private final GitFlowRepository repository;

	public FeatureNameValidator(GitFlowRepository gfRepo) {
		this.repository = gfRepo;
	}

	@Override
	protected boolean branchExists(String newText) throws CoreException {
		return BranchNameValidator.featureExists(repository, newText);
	}
}
