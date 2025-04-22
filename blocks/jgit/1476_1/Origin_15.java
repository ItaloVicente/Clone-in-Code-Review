package org.eclipse.jgit.blame;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.TreeEntry;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;

public class Origin {

	final protected RevCommit commit;


	final protected String filename;

	final protected Repository repository;

	public Origin(Repository repository
		super();
		this.repository = repository;
		this.commit = commit;
		this.filename = filename;
	}

	public ObjectId getObjectId() {
		try {
			RevTree revTree = commit.getTree();
			TreeEntry blobEntry = repository.mapTree(revTree).findBlobMember(
					filename);
			if (blobEntry == null) {
				return ObjectId.zeroId();
			}
			return blobEntry.getId();
		} catch (Exception e) {
			throw new RuntimeException("Error retrieving data for origin "
					+ this
		}
	}

	public byte[] getBytes() {
		try {
			RevTree revTree = commit.getTree();
			TreeEntry blobEntry = repository.mapTree(revTree).findBlobMember(
					filename);
			if (blobEntry == null) {
				return new byte[0];
			}
			ObjectLoader objectLoader = repository.open(blobEntry.getId());
			return objectLoader.getBytes();
		} catch (Exception e) {
			throw new RuntimeException("Error retrieving data for origin "
					+ this);
		}
	}

	public RevCommit getCommit() {
		return commit;
	}

	public Repository getRepository() {
		return repository;
	}


	@Override
	public String toString() {
		return filename + " --> " + commit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((commit == null) ? 0 : commit.getId().hashCode());
		result = prime * result
				+ ((filename == null) ? 0 : filename.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Origin other = (Origin) obj;
		if (commit == null) {
			if (other.commit != null)
				return false;
		} else if (!AnyObjectId.equals(commit
			return false;
		if (filename == null) {
			if (other.filename != null)
				return false;
		} else if (!filename.equals(other.filename))
			return false;
		return true;
	}

}
