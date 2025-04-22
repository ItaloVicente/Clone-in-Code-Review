package org.eclipse.egit.gitflow.ui.internal.actions;

import static org.eclipse.egit.gitflow.ui.Activator.error;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.egit.gitflow.GitFlowRepository;
import org.eclipse.egit.gitflow.op.CurrentBranchPublishOperation;
import org.eclipse.egit.gitflow.ui.Activator;
import org.eclipse.egit.gitflow.ui.internal.JobUtil;
import org.eclipse.egit.ui.UIPreferences;

public abstract class AbstractPublishHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final GitFlowRepository gfRepo = GitFlowHandlerUtil.getRepository(event);

		try {
			int timeout = Activator.getDefault().getPreferenceStore()
					.getInt(UIPreferences.REMOTE_CONNECTION_TIMEOUT);
			CurrentBranchPublishOperation featurePublishOperation = new CurrentBranchPublishOperation(
					gfRepo, timeout);
			JobUtil.scheduleUserWorkspaceJob(featurePublishOperation,
					getProgressText(), JobUtil.GITFLOW_FAMILY);
		} catch (CoreException e) {
			return error(e.getMessage(), e);
		}

		return null;
	}

	abstract protected String getProgressText();
}
