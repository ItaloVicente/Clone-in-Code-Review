package org.eclipse.egit.gitflow.op;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import static org.eclipse.egit.gitflow.Activator.error;

import org.eclipse.egit.gitflow.GitFlowRepository;
import org.eclipse.egit.gitflow.WrongGitFlowStateException;
import org.eclipse.egit.gitflow.internal.CoreText;
import org.eclipse.jgit.revwalk.RevCommit;

public final class FeatureStartOperation extends AbstractFeatureOperation {
	public FeatureStartOperation(GitFlowRepository repository,
			String featureName) {
		super(repository, featureName);
	}

	@Override
	public void execute(IProgressMonitor monitor) throws CoreException {
		String branchName = repository.getConfig().getFeatureBranchName(featureName);

		try {
			if (!repository.isDevelop()) {
				throw new CoreException(
						error(CoreText.FeatureStartOperation_notOn
								+ repository.getConfig().getDevelop()));
			}
		} catch (IOException e) {
			throw new CoreException(error(e.getMessage(), e));
		}
		RevCommit head;
		try {
			head = repository.findHead();
		} catch (WrongGitFlowStateException e) {
			throw new CoreException(error(e));
		}
		start(monitor, branchName, head);
	}
}
