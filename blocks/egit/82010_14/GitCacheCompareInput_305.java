package org.eclipse.egit.ui.internal.synchronize.compare;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;

public class ComparisonDataSource {

	private final RevCommit commit;

	private final ObjectId objectId;

	public ComparisonDataSource(RevCommit commit, ObjectId objectId) {
		this.commit = commit;
		this.objectId = objectId;
	}

	public RevCommit getRevCommit() {
		return commit;
	}

	public ObjectId getObjectId() {
		return objectId;
	}

}
