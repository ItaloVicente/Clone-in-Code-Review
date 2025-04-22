
package org.eclipse.jgit.treewalk.filter;

import java.io.IOException;
import java.util.Collection;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.treewalk.TreeWalk;

public abstract class OrTreeFilter extends TreeFilter {
	public static TreeFilter create(TreeFilter a
		if (a == ALL || b == ALL)
			return ALL;
		return new Binary(a
	}

	public static TreeFilter create(TreeFilter[] list) {
		if (list.length == 2)
			return create(list[0]
		if (list.length < 2)
			throw new IllegalArgumentException(JGitText.get().atLeastTwoFiltersNeeded);
		final TreeFilter[] subfilters = new TreeFilter[list.length];
		System.arraycopy(list
		return new List(subfilters);
	}

	public static TreeFilter create(Collection<TreeFilter> list) {
		if (list.size() < 2)
			throw new IllegalArgumentException(JGitText.get().atLeastTwoFiltersNeeded);
		final TreeFilter[] subfilters = new TreeFilter[list.size()];
		list.toArray(subfilters);
		if (subfilters.length == 2)
			return create(subfilters[0]
		return new List(subfilters);
	}

	private static class Binary extends OrTreeFilter {
		private final TreeFilter a;

		private final TreeFilter b;

		Binary(TreeFilter one
			a = one;
			b = two;
		}

		@Override
		public boolean include(TreeWalk walker)
				throws MissingObjectException
				IOException {
			return matchFilter(walker) <= 0;
		}

		@Override
		public int matchFilter(TreeWalk walker)
				throws MissingObjectException
				IOException {
			final int ra = a.matchFilter(walker);
			if (ra == 0) {
				return 0;
			}
			final int rb = b.matchFilter(walker);
			if (rb == 0) {
				return 0;
			}
			if (ra == -1 || rb == -1) {
				return -1;
			}
			return 1;
		}

		@Override
		public boolean shouldBeRecursive() {
			return a.shouldBeRecursive() || b.shouldBeRecursive();
		}

		@Override
		public TreeFilter clone() {
			return new Binary(a.clone()
		}

		@SuppressWarnings("nls")
		@Override
		public String toString() {
			return "(" + a.toString() + " OR " + b.toString() + ")";
		}
	}

	private static class List extends OrTreeFilter {
		private final TreeFilter[] subfilters;

		List(TreeFilter[] list) {
			subfilters = list;
		}

		@Override
		public boolean include(TreeWalk walker)
				throws MissingObjectException
				IOException {
			return matchFilter(walker) <= 0;
		}

		@Override
		public int matchFilter(TreeWalk walker)
				throws MissingObjectException
				IOException {
			int m = 1;
			for (TreeFilter f : subfilters) {
				int r = f.matchFilter(walker);
				if (r == 0) {
					return 0;
				}
				if (r == -1) {
					m = -1;
				}
			}
			return m;
		}

		@Override
		public boolean shouldBeRecursive() {
			for (TreeFilter f : subfilters)
				if (f.shouldBeRecursive())
					return true;
			return false;
		}

		@Override
		public TreeFilter clone() {
			final TreeFilter[] s = new TreeFilter[subfilters.length];
			for (int i = 0; i < s.length; i++)
				s[i] = subfilters[i].clone();
			return new List(s);
		}

		@Override
		public String toString() {
			final StringBuilder r = new StringBuilder();
			for (int i = 0; i < subfilters.length; i++) {
				if (i > 0)
				r.append(subfilters[i].toString());
			}
			return r.toString();
		}
	}
}
