
package org.eclipse.jgit.treewalk.filter;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.treewalk.TreeWalk;

public class PathSuffixFilter extends TreeFilter {

	public static PathSuffixFilter create(String path) {
		if (path.length() == 0)
			throw new IllegalArgumentException(JGitText.get().emptyPathNotPermitted);
		return new PathSuffixFilter(path);
	}

	final String pathStr;
	final byte[] pathRaw;

	private PathSuffixFilter(String s) {
		pathStr = s;
		pathRaw = Constants.encode(pathStr);
	}

	@Override
	public TreeFilter clone() {
		return this;
	}

	@Override
	public boolean include(TreeWalk walker) throws MissingObjectException
			IncorrectObjectTypeException
		if (walker.isSubtree())
			return true;
		else
			return walker.isPathSuffix(pathRaw

	}

	@Override
	public boolean shouldBeRecursive() {
		return true;
	}

}
