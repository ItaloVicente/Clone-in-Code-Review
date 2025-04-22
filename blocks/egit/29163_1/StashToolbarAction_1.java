package org.eclipse.egit.ui.internal.actions;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.stash.StashCreateUI;
import org.eclipse.jgit.lib.Repository;

public class StashCreateHandler extends RepositoryActionHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		Repository repository = getRepository();
		if (repository == null)
			return null;

		StashCreateUI stashCreateUI = new StashCreateUI(repository);
		stashCreateUI.createStash(getShell(event));

		return null;
	}

	@Override
	public boolean isEnabled() {
		Repository repository = getRepository();
		if (repository == null)
			return false;

		return repository.getRepositoryState().canCommit();
	}
}
