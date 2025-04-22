
package org.eclipse.jgit.treewalk.filter;

import java.io.IOException;

import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;

public abstract class TreeFilter {
	public static final TreeFilter ALL = new AllFilter();

	private static final class AllFilter extends TreeFilter {
		@Override
		public boolean include(TreeWalk walker) {
			return true;
		}

		@Override
		public boolean shouldBeRecursive() {
			return false;
		}

		@Override
		public TreeFilter clone() {
			return this;
		}

		@Override
		public String toString() {
		}
	}

	public static final TreeFilter ANY_DIFF = new AnyDiffFilter();

	private static final class AnyDiffFilter extends TreeFilter {
		private static final int baseTree = 0;

		@Override
		public boolean include(TreeWalk walker) {
			final int n = walker.getTreeCount();
				return true;

			final int m = walker.getRawMode(baseTree);
			for (int i = 1; i < n; i++)
				if (walker.getRawMode(i) != m || !walker.idEqual(i
					return true;
			return false;
		}

		@Override
		public boolean shouldBeRecursive() {
			return false;
		}

		@Override
		public TreeFilter clone() {
			return this;
		}

		@Override
		public String toString() {
		}
	}

	public TreeFilter negate() {
		return NotTreeFilter.create(this);
	}

	public abstract boolean include(TreeWalk walker)
			throws MissingObjectException
			IOException;

	public int matchFilter(TreeWalk walker)
			throws MissingObjectException
			IOException
	{
		return include(walker) ? 0 : 1;
	}

	public abstract boolean shouldBeRecursive();

	@Override
	public abstract TreeFilter clone();

	@Override
	public String toString() {
		String n = getClass().getName();
		int lastDot = n.lastIndexOf('.');
		if (lastDot >= 0) {
			n = n.substring(lastDot + 1);
		}
		return n.replace('$'
	}
}
