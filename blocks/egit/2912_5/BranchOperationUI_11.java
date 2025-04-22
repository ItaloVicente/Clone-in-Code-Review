package org.eclipse.egit.ui.internal.actions;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.branch.BranchOperationUI;
import org.eclipse.jgit.lib.Repository;

public class RenameBranchActionHandler extends RepositoryActionHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		final Repository repository = getRepository(true, event);
		if (repository == null)
			return null;
		BranchOperationUI.rename(repository).start();
		return null;
	}

	@Override
	public boolean isEnabled() {
		return getRepository() != null && containsHead();
	}
}
