package org.eclipse.egit.ui.internal.actions;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.history.GitHistoryPage;
import org.eclipse.team.ui.TeamUI;
import org.eclipse.team.ui.history.IHistoryPage;
import org.eclipse.team.ui.history.IHistoryView;

public class CompareWithRevisionActionHandler extends RepositoryActionHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IHistoryView view = TeamUI.showHistoryFor(getPartPage(event),
				getSelectedResources(event)[0], null);
		if (view == null)
			return null;
		IHistoryPage page = view.getHistoryPage();
		if (page instanceof GitHistoryPage) {
			GitHistoryPage gitHistoryPage = (GitHistoryPage) page;
			gitHistoryPage.setCompareMode(true);
		}
		return null;
	}

	public boolean isEnabled() {
		try {
			return !getSelection(null).isEmpty();
		} catch (ExecutionException e) {
			Activator.handleError(e.getMessage(), e, false);
			return false;
		}
	}
}
