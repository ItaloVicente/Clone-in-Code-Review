package org.eclipse.egit.core.internal.storage;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.egit.core.internal.Utils;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

public class CommitBlobStorage extends BlobStorage {

	private final RevCommit commit;

	CommitBlobStorage(final Repository repository, final String fileName,
			final ObjectId blob, RevCommit commit) {
		super(repository, fileName, blob);
		this.commit = commit;
	}

	@Override
	public IPath getFullPath() {
		IPath repoPath = new Path(Utils.getRepositoryName(db));
		String pathString = super.getFullPath().toPortableString() + " " //$NON-NLS-1$
				+ Utils.getShortObjectId(commit.getId());
		return repoPath.append(Path.fromPortableString(pathString));
	}

}
