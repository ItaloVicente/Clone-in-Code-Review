package org.eclipse.egit.gitflow.ui.internal.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.gitflow.GitFlowRepository;
import org.eclipse.egit.gitflow.op.HotfixStartOperation;
import org.eclipse.egit.gitflow.ui.internal.JobUtil;
import org.eclipse.egit.gitflow.ui.internal.UIText;
import org.eclipse.egit.gitflow.ui.internal.validation.HotfixNameValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.handlers.HandlerUtil;

public class HotfixStartHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final GitFlowRepository gfRepo = GitFlowHandlerUtil.getRepository(event);

		InputDialog inputDialog = new InputDialog(
				HandlerUtil.getActiveShell(event),
				UIText.HotfixStartHandler_provideHotfixName,
				UIText.HotfixStartHandler_pleaseProvideANameForTheNewHotfix,
				"", //$NON-NLS-1$
				new HotfixNameValidator(gfRepo));

		if (inputDialog.open() != Window.OK) {
			return null;
		}

		final String hotfixName = inputDialog.getValue();
		HotfixStartOperation hotfixStartOperation = new HotfixStartOperation(
				gfRepo, hotfixName);
		JobUtil.scheduleUserWorkspaceJob(hotfixStartOperation,
				UIText.HotfixStartHandler_startingNewHotfix,
				JobUtil.GITFLOW_FAMILY);

		return null;
	}
}
