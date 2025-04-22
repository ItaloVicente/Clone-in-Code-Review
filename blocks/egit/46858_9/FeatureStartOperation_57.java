package org.eclipse.egit.gitflow.op;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egit.core.op.RebaseOperation;

import static org.eclipse.egit.gitflow.Activator.error;

import org.eclipse.egit.gitflow.GitFlowRepository;
import org.eclipse.egit.gitflow.WrongGitFlowStateException;
import org.eclipse.egit.gitflow.internal.CoreText;
import org.eclipse.jgit.api.RebaseResult;

public final class FeatureRebaseOperation extends GitFlowOperation {
	private RebaseResult operationResult;

	public FeatureRebaseOperation(GitFlowRepository repository) {
		super(repository);
	}

	@Override
	public void execute(IProgressMonitor monitor) throws CoreException {
		try {
			if (!repository.isFeature()) {
				throw new WrongGitFlowStateException(
						CoreText.FeatureRebaseOperation_notOnAFeatureBranch);
			}

			RebaseOperation op = new RebaseOperation(
					repository.getRepository(), repository.getRepository()
							.getRef(repository.getConfig().getDevelopFull()));
			op.execute(null);

			operationResult = op.getResult();
		} catch (WrongGitFlowStateException | IOException e) {
			throw new CoreException(error(e.getMessage(), e));
		}
	}

	public RebaseResult getOperationResult() {
		return operationResult;
	}
}
