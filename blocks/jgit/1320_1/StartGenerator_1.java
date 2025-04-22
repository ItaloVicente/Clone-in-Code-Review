
package org.eclipse.jgit.revwalk;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;

public interface DepthWalk {

	public enum CompareMode {
		EQUAL

		LESS_THAN_EQUAL
	}

	public int getDepth();

	public CompareMode getCompareMode();

	public class Commit extends RevCommit {

		private int depth;

		protected Commit(AnyObjectId id) {
			super(id);
			depth = Integer.MAX_VALUE;
		}

		public void setDepth(int depth) {
			this.depth = depth;
		}

		public int getDepth() {
			return depth;
		}
	}

	public class RevWalk extends org.eclipse.jgit.revwalk.RevWalk implements DepthWalk {
		private final int depth;
		private final CompareMode compareMode;

		public RevWalk(Repository repo
			super(repo);

			this.depth = depth;
			this.compareMode = compareMode;
		}

		public void markStart(RevCommit c) throws MissingObjectException
		IncorrectObjectTypeException
			if (c instanceof Commit) {
				((Commit)c).setDepth(0);
			}

			super.markStart(c);
		}

		@Override
		protected RevCommit createCommit(final AnyObjectId id) {
			return new Commit(id);
		}

		public int getDepth() {
			return depth;
		}

		public CompareMode getCompareMode() {
			return compareMode;
		}
	}

	public class ObjectWalk extends org.eclipse.jgit.revwalk.ObjectWalk implements DepthWalk {
		private final int depth;
		private final CompareMode compareMode;

		public ObjectWalk(Repository repo
			super(repo);

			this.depth = depth;
			this.compareMode = compareMode;
		}

		public ObjectWalk(ObjectReader or
			super(or);

			this.depth = depth;
			this.compareMode = compareMode;
		}

		public void markStart(RevObject c) throws MissingObjectException
		IncorrectObjectTypeException
			if (c instanceof Commit) {
				((Commit)c).setDepth(0);
			}

			super.markStart(c);
		}

		@Override
		protected RevCommit createCommit(final AnyObjectId id) {
			return new Commit(id);
		}

		public int getDepth() {
			return depth;
		}

		public CompareMode getCompareMode() {
			return compareMode;
		}
	}
}
