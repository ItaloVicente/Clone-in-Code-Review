package org.eclipse.egit.core.synchronize;

import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egit.core.Activator;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.TreeWalk;

class GitRemoteFolder extends GitRemoteResource {

	GitRemoteFolder(Repository repo, RevCommit revCommit, ObjectId objectId,
			String path) {
		super(repo, revCommit, objectId, path);
	}

	public boolean isContainer() {
		return true;
	}

	public boolean equals(Object object) {
		if (object == this)
			return true;

		if (object instanceof GitRemoteFolder) {
			GitRemoteFolder that = (GitRemoteFolder) object;

			return getPath().equals(that.getPath())
					&& getObjectId().equals(that.getObjectId());
		}

		return false;
	}

	public int hashCode() {
		return getObjectId().hashCode() ^ getPath().hashCode();
	}

	GitRemoteResource[] members(IProgressMonitor monitor) {
		String path = getPath();
		Repository repo = getRepo();
		RevCommit revCommit = getCommit();
		List<GitRemoteResource> result = new ArrayList<GitRemoteResource>();

		try {
			TreeWalk tw;
			if (path.length() > 0) {
				tw = TreeWalk.forPath(repo, path, revCommit.getTree());
			} else {
				tw = new TreeWalk(repo);
				tw.addTree(revCommit.getTree());
			}
			while (tw.next()) {
				if (monitor.isCanceled())
					break;

				ObjectId objectId = tw.getObjectId(0);
				if (ObjectId.zeroId().equals(objectId))
					continue;

				GitRemoteResource obj = null;
				int objectType = tw.getFileMode(0).getObjectType();
				if (objectType == OBJ_BLOB)
					obj = new GitRemoteFile(repo, revCommit, objectId, tw.getPathString());
				else if (objectType == Constants.OBJ_TREE)
					obj = new GitRemoteFolder(repo, revCommit, objectId, tw.getPathString());

				if (obj != null)
					result.add(obj);
			}
		} catch (IOException e) {
			Activator.logError(e.getMessage(), e);
		} finally {
			monitor.done();
		}

		return result.toArray(new GitRemoteResource[result.size()]);
	}

}
