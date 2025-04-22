package org.eclipse.egit.ui.internal.history;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revplot.PlotWalk;
import org.eclipse.jgit.revwalk.RevCommit;

class SWTWalk extends PlotWalk {

	private final Repository repo;

	SWTWalk(final Repository repo) {
		super(repo);
		this.repo = repo;
	}

	Repository getRepository() {
		return repo;
	}

	@Override
	protected RevCommit createCommit(final AnyObjectId id) {
		return new SWTCommit(id, this);
	}
}
