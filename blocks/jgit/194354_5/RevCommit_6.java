
package org.eclipse.jgit.revwalk;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.eclipse.jgit.lib.ObjectId;

public class FilteredRevCommit extends RevCommit {
	private static final List<RevCommit> EMPTY = Collections.emptyList();

	List<RevCommit> overriddenParents;

	public FilteredRevCommit(ObjectId id) {
		this(id
	}

	public FilteredRevCommit(RevCommit commit) {
		this(commit
	}

	public FilteredRevCommit(RevCommit commit
		this(commit.getId()
	}

	public FilteredRevCommit(ObjectId id
		super(id);
		this.overriddenParents = parents;
	}

	@Override
	public boolean hasOverriddenParents() {
		return true;
	}

	@Override
	public void setParents(RevCommit... overriddenParents) {
		this.overriddenParents = Arrays.asList(overriddenParents);
	}

	@Override
	public int getParentCount() {
		return overriddenParents == null ? 0 : overriddenParents.size();
	}

	@Override
	public RevCommit getParent(int nth) {
		return overriddenParents.get(nth);
	}

	@Override
	public RevCommit[] getParents() {
		return overriddenParents.toArray(RevCommit[]::new);
	}
}
