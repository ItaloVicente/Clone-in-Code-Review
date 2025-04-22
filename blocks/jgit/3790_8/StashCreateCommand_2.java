package org.eclipse.jgit.api;

import java.util.Set;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;

public class StashApplyCommand extends GitCommand<Set<String>> {

	protected StashApplyCommand(Repository repo) {
		super(repo);
	}

	public Set<String> call() throws Exception {
		ObjectId stashId = repo.resolve(Constants.R_STASH);

		if (stashId == null)
			throw new NoHeadException(
					JGitText.get().cannotRebaseWithoutCurrentHead);

		DirCache dc = repo.lockDirCache();
		DirCacheCheckout dco = new DirCacheCheckout(repo
		dco.checkout();
		return null;
	}
}
