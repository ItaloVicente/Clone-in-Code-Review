
package org.eclipse.jgit.revwalk.filter;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevFlag;
import org.eclipse.jgit.revwalk.RevFlagSet;
import org.eclipse.jgit.revwalk.RevWalk;

public abstract class RevFlagFilter extends RevFilter {
	public static RevFilter has(RevFlag a) {
		final RevFlagSet s = new RevFlagSet();
		s.add(a);
		return new HasAll(s);
	}

	public static RevFilter hasAll(RevFlag... a) {
		final RevFlagSet set = new RevFlagSet();
		for (RevFlag flag : a)
			set.add(flag);
		return new HasAll(set);
	}

	public static RevFilter hasAll(RevFlagSet a) {
		return new HasAll(new RevFlagSet(a));
	}

	public static RevFilter hasAny(RevFlag... a) {
		final RevFlagSet set = new RevFlagSet();
		for (RevFlag flag : a)
			set.add(flag);
		return new HasAny(set);
	}

	public static RevFilter hasAny(RevFlagSet a) {
		return new HasAny(new RevFlagSet(a));
	}

	final RevFlagSet flags;

	RevFlagFilter(RevFlagSet m) {
		flags = m;
	}

	@Override
	public RevFilter clone() {
		return this;
	}

	@Override
	public String toString() {
		return super.toString() + flags;
	}

	private static class HasAll extends RevFlagFilter {
		HasAll(RevFlagSet m) {
			super(m);
		}

		@Override
		public boolean include(RevWalk walker
				throws MissingObjectException
				IOException {
			return c.hasAll(flags);
		}

		@Override
		public boolean requiresCommitBody() {
			return false;
		}
	}

	private static class HasAny extends RevFlagFilter {
		HasAny(RevFlagSet m) {
			super(m);
		}

		@Override
		public boolean include(RevWalk walker
				throws MissingObjectException
				IOException {
			return c.hasAny(flags);
		}

		@Override
		public boolean requiresCommitBody() {
			return false;
		}
	}
}
