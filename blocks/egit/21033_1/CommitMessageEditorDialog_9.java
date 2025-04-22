package org.eclipse.egit.ui.internal.history.command;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.CommonUtils;
import org.eclipse.egit.ui.internal.commit.RepositoryCommit;
import org.eclipse.egit.ui.internal.history.GitHistoryPage;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

public class SquashHandler extends AbstractHistoryCommandHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Repository repository = getRepository(event);
		List<RevCommit> commits = getSelectedCommits();

		List<RepositoryCommit> repositoryCommits = new ArrayList<RepositoryCommit>();
		for (RevCommit commit : commits) {
			repositoryCommits.add(new RepositoryCommit(repository, commit));
		}

		final IStructuredSelection selected = new StructuredSelection(
				repositoryCommits);
		CommonUtils.runCommand(
				org.eclipse.egit.ui.internal.commit.command.SquashHandler.ID,
				selected);

		return null;
	}

	@Override
	public boolean isEnabled() {
		GitHistoryPage page = getPage();
		if (page == null)
			return false;
		IStructuredSelection selection = getSelection(page);
		if (selection.isEmpty())
			return false;

		List elements = selection.toList();
		int parentsNotSelected = 0;
		for (Object element : elements) {
			RevCommit commit = (RevCommit) element;
			if (commit.getParentCount() != 1)
				return false;

			RevCommit parentCommit = commit.getParent(0);
			if (!elements.contains(parentCommit))
				parentsNotSelected++;
			if (parentsNotSelected > 1)
				return false;
		}
		if (parentsNotSelected != 1)
			return false;

		return true;
	}

}
