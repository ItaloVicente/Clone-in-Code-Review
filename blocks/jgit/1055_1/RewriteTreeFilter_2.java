
package org.eclipse.jgit.revwalk;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

public class FollowFilter extends TreeFilter {
	public static FollowFilter create(String path) {
		return new FollowFilter(PathFilter.create(path));
	}

	private final PathFilter path;

	FollowFilter(final PathFilter path) {
		this.path = path;
	}

	public String getPath() {
		return path.getPath();
	}

	@Override
	public boolean include(final TreeWalk walker)
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
		return new FollowFilter(path.clone());
	}

	@Override
	public String toString() {
				+ ANY_DIFF.toString() + ")";
	}
}
