
package org.eclipse.jgit.treewalk.filter;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.treewalk.TreeWalk;

public class NotTreeFilter extends TreeFilter {
	public static TreeFilter create(TreeFilter a) {
		return new NotTreeFilter(a);
	}

	private final TreeFilter a;

	private NotTreeFilter(TreeFilter one) {
		a = one;
	}

	@Override
	public TreeFilter negate() {
		return a;
	}

	@Override
	public boolean include(TreeWalk walker)
			throws MissingObjectException
			IOException {
		return matchFilter(walker) == 0;
	}

	@Override
	public int matchFilter(TreeWalk walker)
			throws MissingObjectException
			IOException {
		final int r = a.matchFilter(walker);
		if (r == 0) {
			return 1;
		}
		if (r == 1) {
			return 0;
		}
		return -1;
	}

	@Override
	public boolean shouldBeRecursive() {
		return a.shouldBeRecursive();
	}

	@Override
	public TreeFilter clone() {
		final TreeFilter n = a.clone();
		return n == a ? this : new NotTreeFilter(n);
	}

	@Override
	public String toString() {
	}
}
