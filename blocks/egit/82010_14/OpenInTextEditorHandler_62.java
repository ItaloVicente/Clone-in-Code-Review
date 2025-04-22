package org.eclipse.egit.ui.internal.history.command;

import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.commit.CommitEditor;
import org.eclipse.egit.ui.internal.commit.RepositoryCommit;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.ui.PartInitException;

public class OpenInCommitViewerHandler extends AbstractHistoryCommandHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Repository repository = getRepository(event);
		List<RevCommit> commits = getSelectedCommits(event);
		for (RevCommit commit : commits) {
			try {
				CommitEditor.open(new RepositoryCommit(repository, commit));
			} catch (PartInitException e) {
				Activator.showError("Error opening commit viewer", e); //$NON-NLS-1$
			}
		}
		return null;
	}
}
