
package org.eclipse.jgit.revwalk;

import java.io.IOException;

import org.eclipse.jgit.diff.DiffConfig;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

public class FollowFilter extends TreeFilter {
	public static FollowFilter create(String path
		return new FollowFilter(PathFilter.create(path)
	}

	private final PathFilter path;
	final DiffConfig cfg;

	private RenameCallback renameCallback;

	FollowFilter(PathFilter path
		this.path = path;
		this.cfg = cfg;
	}

	public String getPath() {
		return path.getPath();
	}

	@Override
	public boolean include(TreeWalk walker)
			throws MissingObjectException
			IOException {
		return path.include(walker) && ANY_DIFF.include(walker);
	}

	@Override
	public boolean shouldBeRecursive() {
		return path.shouldBeRecursive() || ANY_DIFF.shouldBeRecursive();
	}

	@Override
	public TreeFilter clone() {
		return new FollowFilter(path.clone()
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
				+ ANY_DIFF.toString() + ")";
	}

	public RenameCallback getRenameCallback() {
		return renameCallback;
	}

	public void setRenameCallback(RenameCallback callback) {
		renameCallback = callback;
	}
}
