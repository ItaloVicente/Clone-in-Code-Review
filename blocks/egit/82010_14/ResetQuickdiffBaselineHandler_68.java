package org.eclipse.egit.ui.internal.history.command;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.actions.ResetMenu;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.ui.handlers.HandlerUtil;

public class ResetHandler extends AbstractHistoryCommandHandler {
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final Repository repo = getRepository(event);
		final ObjectId commitId = getSelectedCommitId(event);

		String resetMode = event.getParameter(ResetMenu.RESET_MODE);
		ResetMenu.performReset(HandlerUtil.getActiveShellChecked(event), repo,
				commitId, ResetType.valueOf(resetMode));
		return null;
	}
}
