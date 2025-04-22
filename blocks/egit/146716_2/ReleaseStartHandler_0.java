package org.eclipse.egit.gitflow.ui.internal.actions;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.egit.gitflow.GitFlowRepository;
import org.eclipse.egit.gitflow.op.GitFlowOperation;
import org.eclipse.egit.ui.internal.branch.BranchOperationUI;

class PostBranchCheckoutUiTask extends JobChangeAdapter {

	private final GitFlowRepository gfRepo;
	private final String fullBranchName;
	private final GitFlowOperation operation;

	PostBranchCheckoutUiTask(GitFlowRepository gfRepo,
			String fullBranchName, GitFlowOperation operation) {
		this.gfRepo = gfRepo;
		this.fullBranchName = fullBranchName;
		this.operation = operation;
	}

	@Override
	public void done(IJobChangeEvent jobChangeEvent) {
		BranchOperationUI.handleSingleRepositoryCheckoutOperationResult(
				gfRepo.getRepository(),
				operation.getCheckoutResult(),
				fullBranchName);
	}
}
