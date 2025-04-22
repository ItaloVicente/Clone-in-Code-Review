package org.eclipse.egit.core.internal;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;

public interface IRepositoryCommit extends IRepositoryObject {

	RevCommit getRevCommit();

	@Override
	default ObjectId getObjectId() {
		RevCommit commit = getRevCommit();
		return commit == null ? null : commit.getId();
	}

}
