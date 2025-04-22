package org.eclipse.jgit.niofs.internal.op.commands;

import java.io.IOException;

import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.model.PathInfo;
import org.eclipse.jgit.niofs.internal.op.model.PathType;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;

import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;

public class GetPathInfo {

	private final Git git;
	private final String branchName;
	private final String path;

	public GetPathInfo(final Git git
		this.git = git;
		this.branchName = branchName;
		this.path = path;
	}

	public PathInfo execute() throws IOException {

		final String gitPath = PathUtil.normalize(path);

		if (gitPath.isEmpty()) {
			return new PathInfo(null
		}

		final ObjectId tree = git.getTreeFromRef(branchName);
		if (tree == null) {
			return new PathInfo(null
		}
		try (final TreeWalk tw = new TreeWalk(git.getRepository())) {
			tw.setFilter(PathFilter.create(gitPath));
			tw.reset(tree);
			while (tw.next()) {
				if (tw.getPathString().equals(gitPath)) {
					if (tw.getFileMode(0).equals(FileMode.TYPE_TREE)) {
						return new PathInfo(tw.getObjectId(0)
					} else if (tw.getFileMode(0).equals(FileMode.TYPE_FILE)
							|| tw.getFileMode(0).equals(FileMode.EXECUTABLE_FILE)
							|| tw.getFileMode(0).equals(FileMode.REGULAR_FILE)) {
						final long size = tw.getObjectReader().getObjectSize(tw.getObjectId(0)
						return new PathInfo(tw.getObjectId(0)
					}
				}
				if (tw.isSubtree()) {
					tw.enterSubtree();
				}
			}
		}
		return new PathInfo(null
	}
}
