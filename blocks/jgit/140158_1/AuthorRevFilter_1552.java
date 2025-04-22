
package org.eclipse.jgit.revwalk.filter;

import java.io.IOException;
import java.util.Collection;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public abstract class AndRevFilter extends RevFilter {
	public static RevFilter create(RevFilter a
		if (a == ALL)
			return b;
		if (b == ALL)
			return a;
		return new Binary(a
	}

	public static RevFilter create(RevFilter[] list) {
		if (list.length == 2)
			return create(list[0]
		if (list.length < 2)
			throw new IllegalArgumentException(JGitText.get().atLeastTwoFiltersNeeded);
		final RevFilter[] subfilters = new RevFilter[list.length];
		System.arraycopy(list
		return new List(subfilters);
	}

	public static RevFilter create(Collection<RevFilter> list) {
		if (list.size() < 2)
			throw new IllegalArgumentException(JGitText.get().atLeastTwoFiltersNeeded);
		final RevFilter[] subfilters = new RevFilter[list.size()];
		list.toArray(subfilters);
		if (subfilters.length == 2)
			return create(subfilters[0]
		return new List(subfilters);
	}

	private static class Binary extends AndRevFilter {
		private final RevFilter a;

		private final RevFilter b;

		private final boolean requiresCommitBody;

		Binary(RevFilter one
			a = one;
			b = two;
			requiresCommitBody = a.requiresCommitBody()
					|| b.requiresCommitBody();
		}

		@Override
		public boolean include(RevWalk walker
				throws MissingObjectException
				IOException {
			return a.include(walker
		}

		@Override
		public boolean requiresCommitBody() {
			return requiresCommitBody;
		}

		@Override
		public RevFilter clone() {
			return new Binary(a.clone()
		}

		@SuppressWarnings("nls")
		@Override
		public String toString() {
		}
	}

	private static class List extends AndRevFilter {
		private final RevFilter[] subfilters;

		private final boolean requiresCommitBody;

		List(RevFilter[] list) {
			subfilters = list;

			boolean rcb = false;
			for (RevFilter filter : subfilters)
				rcb |= filter.requiresCommitBody();
			requiresCommitBody = rcb;
		}

		@Override
		public boolean include(RevWalk walker
				throws MissingObjectException
				IOException {
			for (RevFilter f : subfilters) {
				if (!f.include(walker
					return false;
			}
			return true;
		}

		@Override
		public boolean requiresCommitBody() {
			return requiresCommitBody;
		}

		@Override
		public RevFilter clone() {
			final RevFilter[] s = new RevFilter[subfilters.length];
			for (int i = 0; i < s.length; i++)
				s[i] = subfilters[i].clone();
			return new List(s);
		}

		@SuppressWarnings("nls")
		@Override
		public String toString() {
			final StringBuilder r = new StringBuilder();
			r.append("(");
			for (int i = 0; i < subfilters.length; i++) {
				if (i > 0)
					r.append(" AND ");
				r.append(subfilters[i].toString());
			}
			r.append(")");
			return r.toString();
		}
	}
}
