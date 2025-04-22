package org.eclipse.egit.ui.internal.reflog.command;


import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;

public class CopyHandler extends AbstractReflogCommandHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		Repository repo = getRepository(event);
		RevCommit commit = getSelectedCommit(event, repo);
		if (commit != null) {
			Clipboard clipboard = new Clipboard(null);
			try {
				TextTransfer textTransfer = TextTransfer.getInstance();
				Transfer[] transfers = new Transfer[] { textTransfer };
				Object[] data = new Object[] { ObjectId.toString(commit) };
				clipboard.setContents(data, transfers);
			} finally {
				clipboard.dispose();
			}
		}
		return null;
	}
}
