package org.eclipse.jgit.revwalk;

import org.eclipse.jgit.lib.AnyObjectId;

public class FilteredRevCommit extends RevCommit {
	private RevCommit[] overriddenParents;

	public FilteredRevCommit(AnyObjectId commit) {
		this(commit
	}

	public FilteredRevCommit(AnyObjectId commit
		super(commit);
		this.overriddenParents = parents;
		this.parents = NO_PARENTS;
	}

	public void setParents(RevCommit... overriddenParents) {
		this.overriddenParents = overriddenParents;
	}

	@Override
	public int getParentCount() {
		return overriddenParents.length;
	}

	@Override
	public RevCommit getParent(int nth) {
		return overriddenParents[nth];
	}

	@Override
	public RevCommit[] getParents() {
		return overriddenParents;
	}
}
