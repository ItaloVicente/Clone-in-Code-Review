package org.eclipse.egit.core.synchronize;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

class GitRemoteFile extends GitRemoteResource {

	GitRemoteFile(Repository repo, RevCommit revCommit,
			ObjectId objectId, String path) {
		super(repo, revCommit, objectId, path);
	}

	public boolean isContainer() {
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;

		if (obj instanceof GitRemoteFile) {
			GitRemoteFile that = (GitRemoteFile) obj;

			return getPath().equals(that.getPath())
					&& getObjectId().equals(that.getObjectId());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return getObjectId().hashCode() ^ getPath().hashCode();
	}

}
