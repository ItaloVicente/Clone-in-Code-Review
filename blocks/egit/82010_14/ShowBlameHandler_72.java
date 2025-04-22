package org.eclipse.egit.ui.internal.history.command;

import java.io.IOException;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.decorators.GitQuickDiffProvider;
import org.eclipse.egit.ui.internal.history.GitHistoryPage;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jgit.lib.Repository;

public class SetQuickdiffBaselineHandler extends AbstractHistoryCommandHandler {
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Repository repo = getRepository(event);
		String baseline = getSelectedCommitId(event).name();
		if (repo == null)
			throw new ExecutionException(
					UIText.ResetQuickdiffBaselineHandler_NoTargetMessage);

		try {
			GitQuickDiffProvider.setBaselineReference(repo, baseline);
		} catch (IOException e) {
			throw new ExecutionException(e.getMessage(), e);
		}

		return null;
	}

	@Override
	public boolean isEnabled() {
		GitHistoryPage page = getPage();
		if (page == null)
			return false;
		IStructuredSelection selection = getSelection(page);
		return selection.size() == 1;
	}
}
