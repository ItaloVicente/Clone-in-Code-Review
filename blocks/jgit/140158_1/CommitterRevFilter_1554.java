
package org.eclipse.jgit.revwalk.filter;

import java.io.IOException;
import java.util.Date;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public abstract class CommitTimeRevFilter extends RevFilter {
	public static final RevFilter before(Date ts) {
		return before(ts.getTime());
	}

	public static final RevFilter before(long ts) {
		return new Before(ts);
	}

	public static final RevFilter after(Date ts) {
		return after(ts.getTime());
	}

	public static final RevFilter after(long ts) {
		return new After(ts);
	}

	public static final RevFilter between(Date since
		return between(since.getTime()
	}

	public static final RevFilter between(long since
		return new Between(since
	}

	final int when;

	CommitTimeRevFilter(long ts) {
		when = (int) (ts / 1000);
	}

	@Override
	public RevFilter clone() {
		return this;
	}

	@Override
	public boolean requiresCommitBody() {
		return false;
	}

	private static class Before extends CommitTimeRevFilter {
		Before(long ts) {
			super(ts);
		}

		@Override
		public boolean include(RevWalk walker
				throws StopWalkException
				IncorrectObjectTypeException
			return cmit.getCommitTime() <= when;
		}

		@SuppressWarnings("nls")
		@Override
		public String toString() {
			return super.toString() + "(" + new Date(when * 1000L) + ")";
		}
	}

	private static class After extends CommitTimeRevFilter {
		After(long ts) {
			super(ts);
		}

		@Override
		public boolean include(RevWalk walker
				throws StopWalkException
				IncorrectObjectTypeException
			if (cmit.getCommitTime() < when)
				throw StopWalkException.INSTANCE;
			return true;
		}

		@SuppressWarnings("nls")
		@Override
		public String toString() {
			return super.toString() + "(" + new Date(when * 1000L) + ")";
		}
	}

	private static class Between extends CommitTimeRevFilter {
		private final int until;

		Between(long since
			super(since);
			this.until = (int) (until / 1000);
		}

		@Override
		public boolean include(RevWalk walker
				throws StopWalkException
				IncorrectObjectTypeException
			return cmit.getCommitTime() <= until && cmit.getCommitTime() >= when;
		}

		@SuppressWarnings("nls")
		@Override
		public String toString() {
			return super.toString() + "(" + new Date(when * 1000L) + " - "
					+ new Date(until * 1000L) + ")";
		}

	}

}
