
package org.eclipse.egit.ui.internal.history.command;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.core.CoreText;
import org.eclipse.jgit.api.CherryPickCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

public class CherryPickHandler extends AbstractHistoryCommanndHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		RevCommit commit = (RevCommit) getSelection(getPage()).getFirstElement();
		RevCommit newHead;
		Repository repo = getRepository(event);

		CherryPickCommand cherryPick;
		Git git = new Git(repo);
		try {
			cherryPick = git.cherryPick().include(commit.getId());
			newHead = cherryPick.call();
		} catch (Exception e) {
			throw new ExecutionException(CoreText.CherryPickOperation_InternalError, e);
		}
		if (newHead == null)
			throw new ExecutionException(CoreText.CherryPickOperation_Failed);
		return null;
	}
}
