package org.eclipse.jgit.api;

import java.util.Set;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;

public class StashApplyCommand extends GitCommand<Set<String>> {

	protected StashApplyCommand(Repository repo) {
		super(repo);
	}

	public Set<String> call() throws Exception {
		ObjectId stashId = repo.resolve(Constants.R_STASH);

		MergeCommand merge = new MergeCommand(repo);
		merge.include(stashId);

		return null;
	}
}
