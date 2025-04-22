
package org.eclipse.egit.ui.internal.history.command;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.CommonUtils;
import org.eclipse.egit.ui.internal.commit.RepositoryCommit;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryState;
import org.eclipse.jgit.revwalk.RevCommit;

public class CherryPickHandler extends AbstractHistoryCommandHandler {

	@Override
	public boolean isEnabled() {
		final Repository repository = getRepository(getPage());
		if (repository == null)
			return false;
		return repository.getRepositoryState().equals(RepositoryState.SAFE);
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		RevCommit commit = getSelectedCommit(event);
		Repository repo = getRepository(event);
		if (repo == null)
			return null;

		final IStructuredSelection selected = new StructuredSelection(
				new RepositoryCommit(repo, commit));
		CommonUtils
				.runCommand(
						org.eclipse.egit.ui.internal.commit.command.CherryPickHandler.ID,
						selected);
		return null;
	}
}
