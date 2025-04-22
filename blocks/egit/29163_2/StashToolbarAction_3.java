package org.eclipse.egit.ui.internal.actions;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.internal.indexdiff.IndexDiffCacheEntry;
import org.eclipse.egit.core.internal.indexdiff.IndexDiffData;
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
		return isEnabled(repository);
	}

	static boolean isEnabled(Repository repository) {
		if (repository == null)
			return false;

		if (!repository.getRepositoryState().canCommit())
			return false;

		IndexDiffCacheEntry entry = Activator.getDefault()
				.getIndexDiffCache().getIndexDiffCacheEntry(repository);
		if (entry == null)
			return false;

		IndexDiffData diff = entry.getIndexDiff();
		if (diff == null)
			return false;

		if (diff.getAdded().isEmpty() && diff.getChanged().isEmpty()
				&& diff.getRemoved().isEmpty() && diff.getUntracked().isEmpty()
				&& diff.getModified().isEmpty() && diff.getMissing().isEmpty())
			return false;

		return true;
	}
}
