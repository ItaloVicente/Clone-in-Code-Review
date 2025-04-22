package org.eclipse.egit.ui.internal.reflog.command;


import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.branch.BranchOperationUI;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

public class CheckoutHandler extends AbstractReflogCommandHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Repository repo = getRepository(event);
		RevCommit commit = getSelectedCommit(event, repo);
		if (commit != null) {
			final BranchOperationUI op = BranchOperationUI.checkout(repo,
					commit.name());
			if (op != null)
				op.start();
		}
		return null;
	}
}
