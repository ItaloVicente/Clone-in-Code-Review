package org.eclipse.egit.core.op;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.eclipse.core.resources.IResource;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.FileUtils;

public class ReplaceFromHEADOperation extends DiscardChangesOperation {

	public ReplaceFromHEADOperation(IResource[] files) {
		super(files);
	}

	protected void discardChange(IResource res, Repository repository)
			throws IOException {

		String resRelPath = RepositoryMapping.getMapping(res).getRepoRelativePath(res);
		File f = new File(res.getLocationURI());

		RevCommit commit = new RevWalk(repository).parseCommit(repository.getRef(Constants.HEAD).getObjectId());

		TreeWalk w = TreeWalk.forPath(repository, resRelPath, commit.getTree());

		ObjectLoader ol = repository.open(w.getObjectId(0));

		File parentDir = f.getParentFile();
		File tmpFile = File.createTempFile("._" + f.getName(), null, parentDir); //$NON-NLS-1$
		FileOutputStream channel = new FileOutputStream(tmpFile);
		try {
			ol.copyTo(channel);
		} finally {
			channel.close();
		}

		if (!tmpFile.renameTo(f)) {
			FileUtils.delete(f);
			if (!tmpFile.renameTo(f))
				throw new IOException("Can't write to file " + f.getPath()); //$NON-NLS-1$
		}
	}

}
