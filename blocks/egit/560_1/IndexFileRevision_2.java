package org.eclipse.egit.core.internal.storage;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.egit.core.internal.Utils;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;

public class IndexBlobStorage extends BlobStorage {

	IndexBlobStorage(final Repository repository, final String fileName,
			final ObjectId blob) {
		super(repository, fileName, blob);
	}

	@Override
	public IPath getFullPath() {
		IPath repoPath = new Path(Utils.getRepositoryName(db));
		String pathString = super.getFullPath().toPortableString() + " index"; //$NON-NLS-1$
		return repoPath.append(Path.fromPortableString(pathString));
	}

}
