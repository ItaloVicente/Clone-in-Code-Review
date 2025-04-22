package org.eclipse.egit.gitflow.ui.internal.actions;

import static org.eclipse.egit.gitflow.ui.Activator.error;

import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.egit.gitflow.GitFlowRepository;
import org.eclipse.egit.gitflow.WrongGitFlowStateException;
import org.eclipse.egit.gitflow.op.HotfixFinishOperation;
import org.eclipse.egit.gitflow.ui.internal.JobUtil;
import org.eclipse.egit.gitflow.ui.internal.UIText;

public class HotfixFinishHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final GitFlowRepository gfRepo = GitFlowHandlerUtil.getRepository(event);

		HotfixFinishOperation hotfixFinishOperation;
		try {
			hotfixFinishOperation = new HotfixFinishOperation(gfRepo);
			JobUtil.scheduleUserWorkspaceJob(hotfixFinishOperation,
					UIText.HotfixFinishHandler_finishingHotfix,
					JobUtil.GITFLOW_FAMILY);
		} catch (WrongGitFlowStateException | CoreException | IOException e) {
			return error(e.getMessage(), e);
		}

		return null;
	}
}
