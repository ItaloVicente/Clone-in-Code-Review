package org.eclipse.egit.ui.internal.history;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jgit.revwalk.RevCommit;

public class GitCommitReference {

	private final RevCommit target;

	private final IRegion region;

	public GitCommitReference(@NonNull RevCommit target, @NonNull IRegion region) {
		this.target = target;
		this.region = region;
	}

	public IRegion getRegion() {
		return region;
	}

	public RevCommit getTarget() {
		return target;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof GitCommitReference) {
			GitCommitReference other = (GitCommitReference) obj;
			return target.equals(other.target) && region.equals(other.region);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return target.hashCode() ^ region.hashCode();
	}

}
