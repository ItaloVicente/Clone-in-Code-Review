package org.eclipse.jgit.revwalk;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.revwalk.RevCommit;

public class FakeRevCommit extends RevCommit {
	public FakeRevCommit(AnyObjectId id) {
		super(id);
	}

	public void setParents(RevCommit[] parents) {
		this.parents = parents;
	}
}
