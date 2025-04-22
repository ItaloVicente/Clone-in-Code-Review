package org.eclipse.egit.ui.internal.reflog.command;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.actions.ResetMenu;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.ui.handlers.HandlerUtil;

public class ResetHandler extends AbstractReflogCommandHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Repository repository = getRepository(event);
		String resetMode = event.getParameter(ResetMenu.RESET_MODE);
		RevCommit commit = getSelectedCommit(event, repository);
		if (commit != null)
			ResetMenu.performReset(HandlerUtil.getActiveShellChecked(event),
					repository, commit, ResetType.valueOf(resetMode));
		return null;
	}

}
