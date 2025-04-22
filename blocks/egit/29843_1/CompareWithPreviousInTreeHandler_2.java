package org.eclipse.egit.ui.internal.history.command;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.history.GitHistoryPage;
import org.eclipse.jgit.revwalk.RevCommit;

public class CompareWithPreviousHandler extends AbstractHistoryCompareCommandHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		RevCommit commit = getSelectedCommit(event);

		if (commit.getParentCount() == 1) {
			compare(commit, commit.getParent(0), event);
		}

		return null;
	}

	@Override
	public boolean isEnabled() {
		GitHistoryPage page = getPage();
		if (page == null)
			return false;
		if (getSelection(page).size() == 1) {
			RevCommit commit = (RevCommit) getSelection(page).getFirstElement();
			if (commit.getParentCount() == 1)
				return true;
		}
		return false;
	}
}
