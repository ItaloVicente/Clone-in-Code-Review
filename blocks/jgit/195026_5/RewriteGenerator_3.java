package org.eclipse.jgit.revwalk;

import org.eclipse.jgit.lib.ObjectId;

public class FilteredRevCommit extends RevCommit {
	private RevCommit[] overriddenParents;

	public FilteredRevCommit(ObjectId id) {
		this(id
	}

	public FilteredRevCommit(RevCommit commit) {
		this(commit
	}

	public FilteredRevCommit(RevCommit commit
		this(commit.getId()
		this.flags = commit.flags;
		this.flags = this.flags &= ~RevObject.PARSED;
		this.commitTime = commit.commitTime;
	}

	public FilteredRevCommit(ObjectId id
		super(id);
		this.overriddenParents = parents;
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
