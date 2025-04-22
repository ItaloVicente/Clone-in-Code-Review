package org.eclipse.egit.core.op;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IResource;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.lib.Repository;

public class ReplaceFromIndexOperation extends DiscardChangesOperation {

	public ReplaceFromIndexOperation(IResource[] files) {
		super(files);
	}

	protected void discardChange(IResource res, Repository repository)
			throws IOException {
		String resRelPath = RepositoryMapping.getMapping(res)
				.getRepoRelativePath(res);
		DirCache dc = repository.lockDirCache();
		try {
			DirCacheEntry entry = dc.getEntry(resRelPath);
			if (entry != null) {
				File file = new File(res.getLocationURI());
				DirCacheCheckout.checkoutEntry(repository, file, entry);
			}
		} finally {
			dc.unlock();
		}
	}

}
