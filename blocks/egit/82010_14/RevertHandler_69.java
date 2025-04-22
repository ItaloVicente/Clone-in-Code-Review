package org.eclipse.egit.ui.internal.history.command;

import java.io.IOException;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.decorators.GitQuickDiffProvider;
import org.eclipse.jgit.lib.Repository;

public class ResetQuickdiffBaselineHandler extends
		AbstractHistoryCommandHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		String baseline = event
				.getParameter(HistoryViewCommands.BASELINE_TARGET);
		Repository repo = getRepository(event);
		if (baseline == null)
			throw new ExecutionException(
					UIText.ResetQuickdiffBaselineHandler_NoTargetMessage);

		try {
			GitQuickDiffProvider.setBaselineReference(repo, baseline);
		} catch (IOException e) {
			throw new ExecutionException(e.getMessage(), e);
		}
		return null;
	}
}
