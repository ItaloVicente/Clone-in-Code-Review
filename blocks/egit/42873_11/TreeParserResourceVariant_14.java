package org.eclipse.egit.core.internal.storage;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.team.core.TeamException;

public class IndexResourceVariant extends AbstractGitResourceVariant {
	private IndexResourceVariant(Repository repository, String path,
			boolean isContainer, ObjectId objectId, int rawMode) {
		super(repository, path, isContainer, objectId, rawMode);
	}

	public static IndexResourceVariant create(Repository repository,
			DirCacheEntry entry) {
		final String path = entry.getPathString();
		final boolean isContainer = FileMode.TREE.equals(entry.getFileMode());
		final ObjectId objectId = entry.getObjectId();
		final int rawMode = entry.getRawMode();

		return new IndexResourceVariant(repository, path, isContainer,
				objectId, rawMode);
	}

	public IStorage getStorage(IProgressMonitor monitor) throws TeamException {
		return new IndexBlobStorage(repository, path, objectId);
	}
}
