package org.eclipse.jgit.revwalk;

public class FilteredRevCommit extends RevCommit {
	private RevCommit[] overriddenParents;

	public FilteredRevCommit(RevCommit commit) {
		this(commit
	}

	public FilteredRevCommit(RevCommit commit
		super(commit);
		this.overriddenParents = parents;
		commit.copy(this);
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
