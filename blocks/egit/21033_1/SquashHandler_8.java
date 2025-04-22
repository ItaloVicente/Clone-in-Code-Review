package org.eclipse.egit.ui.internal.history.command;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.CommonUtils;
import org.eclipse.egit.ui.internal.commit.RepositoryCommit;
import org.eclipse.egit.ui.internal.history.GitHistoryPage;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

public class RewordHandler extends AbstractHistoryCommandHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Repository repository = getRepository(event);
		RevCommit commit = (RevCommit) getSelection(getPage())
				.getFirstElement();

		final IStructuredSelection selected = new StructuredSelection(
				new RepositoryCommit(repository, commit));
		CommonUtils
				.runCommand(
						org.eclipse.egit.ui.internal.commit.command.RewordHandler.ID,
						selected);

		return null;
	}

	@Override
	public boolean isEnabled() {
		GitHistoryPage page = getPage();
		if (page == null)
			return false;
		IStructuredSelection selection = getSelection(page);
		if (selection.size() != 1)
			return false;
		RevCommit commit = (RevCommit) selection.getFirstElement();
		return (commit.getParentCount() == 1);
	}
}
