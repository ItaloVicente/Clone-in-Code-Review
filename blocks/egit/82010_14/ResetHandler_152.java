package org.eclipse.egit.ui.internal.reflog.command;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.commit.CommitEditor;
import org.eclipse.egit.ui.internal.commit.RepositoryCommit;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

public class OpenInCommitViewerHandler extends AbstractReflogCommandHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Repository repository = getRepository(event);
		RevCommit commit = getSelectedCommit(event, repository);
		if (commit != null)
			CommitEditor.openQuiet((new RepositoryCommit(repository, commit)));
		return null;
	}

}
