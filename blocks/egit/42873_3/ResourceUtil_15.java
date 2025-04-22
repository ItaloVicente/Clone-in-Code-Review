package org.eclipse.egit.core.internal.storage;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.team.core.TeamException;

public class TreeParserResourceVariant extends AbstractGitResourceVariant {
	private TreeParserResourceVariant(Repository repository, String path,
			String fileName, boolean isContainer, ObjectId objectId, int rawMode) {
		super(repository, path, fileName, isContainer, objectId, rawMode);
	}

	public static TreeParserResourceVariant create(Repository repository,
			CanonicalTreeParser treeParser) {
		final String path = treeParser.getEntryPathString();
		final String fileName;
		int lastSeparator = path.lastIndexOf('/');
		if (lastSeparator > 0)
			fileName = path.substring(lastSeparator + 1);
		else
			fileName = path;

		final boolean isContainer = FileMode.TREE.equals(treeParser
				.getEntryFileMode());
		final ObjectId objectId = treeParser.getEntryObjectId();
		final int rawMode = treeParser.getEntryRawMode();

		return new TreeParserResourceVariant(repository, path, fileName,
				isContainer, objectId, rawMode);
	}

	public IStorage getStorage(IProgressMonitor monitor) throws TeamException {
		return new BlobStorage(repository, path, objectId);
	}
}
