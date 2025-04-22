package org.eclipse.egit.gitflow.ui.internal.actions;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.egit.gitflow.GitFlowRepository;
import org.eclipse.egit.gitflow.op.CurrentBranchPublishOperation;
import org.eclipse.egit.ui.internal.push.PushMode;
import org.eclipse.egit.ui.internal.push.ShowPushResultAction;
import org.eclipse.jface.action.Action;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.ui.PlatformUI;

class PostPublishUiTask extends JobChangeAdapter {

	private final GitFlowRepository gfRepo;

	private final CurrentBranchPublishOperation operation;

	PostPublishUiTask(GitFlowRepository gfRepo,
			CurrentBranchPublishOperation operation) {
		this.gfRepo = gfRepo;
		this.operation = operation;
	}

	@Override
	public void done(IJobChangeEvent jobChangeEvent) {
		Action resultAction = new ShowPushResultAction(
				gfRepo.getRepository(),
				operation.getOperationResult(),
				Constants.DEFAULT_REMOTE_NAME,
				false,
				PushMode.UPSTREAM);
		PlatformUI.getWorkbench().getDisplay().asyncExec(() -> {
			resultAction.run();
		});
	}
}
