package org.eclipse.egit.core;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public class UtilWalk extends RevWalk {
	public UtilWalk(Repository repo) {
		super(repo);
	}

	@Override
	protected RevCommit createCommit(AnyObjectId id) {
		return new UtilCommit(id);
	}
}
