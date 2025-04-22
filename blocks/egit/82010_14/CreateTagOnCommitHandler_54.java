package org.eclipse.egit.ui.internal.history.command;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.history.GitHistoryPage;
import org.eclipse.egit.ui.internal.patch.PatchOperationUI;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

public class CreatePatchHandler extends AbstractHistoryCommandHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		List<RevCommit> selectedCommits = getSelectedCommits(event);
		RevCommit commit = selectedCommits.get(0);
		Repository repo = getRepository(event);
		PatchOperationUI.createPatch(getPart(event), commit, repo).start();
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
		return (commit.getParentCount() <= 1);
	}
}
