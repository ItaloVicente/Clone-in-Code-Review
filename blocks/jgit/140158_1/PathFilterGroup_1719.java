
package org.eclipse.jgit.treewalk.filter;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.treewalk.TreeWalk;

public class PathFilter extends TreeFilter {
	public static PathFilter create(String path) {
			path = path.substring(0
		if (path.length() == 0)
			throw new IllegalArgumentException(JGitText.get().emptyPathNotPermitted);
		return new PathFilter(path);
	}

	final String pathStr;

	final byte[] pathRaw;

	private PathFilter(String s) {
		pathStr = s;
		pathRaw = Constants.encode(pathStr);
	}

	public String getPath() {
		return pathStr;
	}

	@Override
	public boolean include(TreeWalk walker) {
		return matchFilter(walker) <= 0;
	}

	@Override
	public int matchFilter(TreeWalk walker) {
		return walker.isPathMatch(pathRaw
	}

	@Override
	public boolean shouldBeRecursive() {
		for (byte b : pathRaw)
			if (b == '/')
				return true;
		return false;
	}

	@Override
	public PathFilter clone() {
		return this;
	}

	@Override
	@SuppressWarnings("nls")
	public String toString() {
		return "PATH(\"" + pathStr + "\")";
	}

	public boolean isDone(TreeWalk walker) {
		return pathRaw.length == walker.getPathLength();
	}
}
