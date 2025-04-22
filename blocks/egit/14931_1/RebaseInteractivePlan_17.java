package org.eclipse.egit.ui.internal.rebase;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryState;

public class RebaseInteractiveInput {

	private final RebaseInteractivePlan plan;

	private final Repository repo;

	private boolean rebaseHasBeenInitializedButNotStartedYet = false;

	RebaseInteractiveInput(Repository repo) {
		Assert.isNotNull(repo);
		this.repo = repo;
		this.plan = new RebaseInteractivePlan();
		this.setRebaseHasBeenInitializedButNotStartedYet(false);
		reload();
	}

	public boolean hasRebaseBeenInitializedButNotStartedYet() {
		return rebaseHasBeenInitializedButNotStartedYet;
	}

	void setRebaseHasBeenInitializedButNotStartedYet(
			boolean rebaseHasBeenInitializedButNotStartedYet) {
		this.rebaseHasBeenInitializedButNotStartedYet = rebaseHasBeenInitializedButNotStartedYet;
	}

	public final RebaseInteractivePlan getPlan() {
		return plan;
	}

	public final Repository getRepo() {
		return repo;
	}

	public boolean reload() {
		return plan.parse(repo);
	}

	public boolean persist() {
		if (!checkState())
			return false;
		return plan.persist(repo);
	}

	public boolean checkState() {
		return repo.getRepositoryState() == RepositoryState.REBASING_INTERACTIVE;
	}
}
