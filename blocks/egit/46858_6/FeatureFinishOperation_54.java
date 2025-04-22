package org.eclipse.egit.gitflow.op;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egit.core.op.BranchOperation;
import org.eclipse.egit.gitflow.GitFlowRepository;
import org.eclipse.jgit.api.CheckoutResult;

public final class FeatureCheckoutOperation extends AbstractFeatureOperation {
	private CheckoutResult result;

	public FeatureCheckoutOperation(GitFlowRepository repository,
			String featureName) {
		super(repository, featureName);
	}

	@Override
	public void execute(IProgressMonitor monitor) throws CoreException {
		String branchName = repository.getConfig().getFeatureBranchName(featureName);

		boolean dontCloseProjects = false;
		BranchOperation branchOperation = new BranchOperation(
				repository.getRepository(), branchName, dontCloseProjects);
		branchOperation.execute(null);
		result = branchOperation.getResult();
	}

	public CheckoutResult getResult() {
		return result;
	}
}
