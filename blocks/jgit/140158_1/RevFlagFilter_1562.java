
package org.eclipse.jgit.revwalk.filter;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public abstract class RevFilter {
	public static final RevFilter ALL = new AllFilter();

	private static final class AllFilter extends RevFilter {
		@Override
		public boolean include(RevWalk walker
			return true;
		}

		@Override
		public RevFilter clone() {
			return this;
		}

		@Override
		public boolean requiresCommitBody() {
			return false;
		}

		@Override
		public String toString() {
		}
	}

	public static final RevFilter NONE = new NoneFilter();

	private static final class NoneFilter extends RevFilter {
		@Override
		public boolean include(RevWalk walker
			return false;
		}

		@Override
		public RevFilter clone() {
			return this;
		}

		@Override
		public boolean requiresCommitBody() {
			return false;
		}

		@Override
		public String toString() {
		}
	}

	public static final RevFilter ONLY_MERGES = new OnlyMergesFilter();

	private static final class OnlyMergesFilter extends RevFilter {

		@Override
		public boolean include(RevWalk walker
			return c.getParentCount() >= 2;
		}

		@Override
		public RevFilter clone() {
			return this;
		}

		@Override
		public boolean requiresCommitBody() {
			return false;
		}

		@Override
		public String toString() {
		}
	}

	public static final RevFilter NO_MERGES = new NoMergesFilter();

	private static final class NoMergesFilter extends RevFilter {
		@Override
		public boolean include(RevWalk walker
			return c.getParentCount() < 2;
		}

		@Override
		public RevFilter clone() {
			return this;
		}

		@Override
		public boolean requiresCommitBody() {
			return false;
		}

		@Override
		public String toString() {
		}
	}

	public static final RevFilter MERGE_BASE = new MergeBaseFilter();

	private static final class MergeBaseFilter extends RevFilter {
		@Override
		public boolean include(RevWalk walker
			throw new UnsupportedOperationException(JGitText.get().cannotBeCombined);
		}

		@Override
		public RevFilter clone() {
			return this;
		}

		@Override
		public boolean requiresCommitBody() {
			return false;
		}

		@Override
		public String toString() {
		}
	}

	public RevFilter negate() {
		return NotRevFilter.create(this);
	}

	public boolean requiresCommitBody() {
		return true;
	}

	public abstract boolean include(RevWalk walker
			throws StopWalkException
			IncorrectObjectTypeException

	@Override
	public abstract RevFilter clone();

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
