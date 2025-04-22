package org.eclipse.egit.ui.internal.actions;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.egit.ui.internal.history.GitHistoryPage;
import org.eclipse.team.ui.TeamUI;
import org.eclipse.team.ui.history.IHistoryPage;
import org.eclipse.team.ui.history.IHistoryView;

public class CompareWithRevisionActionHandler extends RepositoryActionHandler {

	public Object execute(ExecutionEvent event) {
		IHistoryView view = TeamUI.showHistoryFor(getPartPage(event),
				getSelectedResources()[0], null);
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
		return !getSelection().isEmpty();
	}
}
