
package org.eclipse.jgit.revwalk;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;

public interface DepthWalk {
	public int getDepth();

	public RevFlag getUnshallowFlag();

	public RevFlag getReinterestingFlag();

	public static class Commit extends RevCommit {
		int depth;

		public int getDepth() {
			return depth;
		}

		protected Commit(AnyObjectId id) {
			super(id);
			depth = -1;
		}
	}

	public class RevWalk extends org.eclipse.jgit.revwalk.RevWalk implements DepthWalk {
		private final int depth;

		private final RevFlag UNSHALLOW;

		private final RevFlag REINTERESTING;

		public RevWalk(Repository repo
			super(repo);

			this.depth = depth;
			this.UNSHALLOW = newFlag("UNSHALLOW");
			this.REINTERESTING = newFlag("REINTERESTING");
		}

		public RevWalk(ObjectReader or
			super(or);

			this.depth = depth;
			this.UNSHALLOW = newFlag("UNSHALLOW");
			this.REINTERESTING = newFlag("REINTERESTING");
		}

		public void markRoot(RevCommit c) throws MissingObjectException
				IncorrectObjectTypeException
			if (c instanceof Commit)
				((Commit) c).depth = 0;
			super.markStart(c);
		}

		@Override
		protected RevCommit createCommit(AnyObjectId id) {
			return new Commit(id);
		}

		public int getDepth() {
			return depth;
		}

		public RevFlag getUnshallowFlag() {
			return UNSHALLOW;
		}

		public RevFlag getReinterestingFlag() {
			return REINTERESTING;
		}
	}

	public class ObjectWalk extends org.eclipse.jgit.revwalk.ObjectWalk implements DepthWalk {
		private final int depth;

		private final RevFlag UNSHALLOW;

		private final RevFlag REINTERESTING;

		public ObjectWalk(Repository repo
			super(repo);

			this.depth = depth;
			this.UNSHALLOW = newFlag("UNSHALLOW");
			this.REINTERESTING = newFlag("REINTERESTING");
		}

		public ObjectWalk(ObjectReader or
			super(or);

			this.depth = depth;
			this.UNSHALLOW = newFlag("UNSHALLOW");
			this.REINTERESTING = newFlag("REINTERESTING");
		}

		public void markRoot(RevObject o) throws MissingObjectException
				IncorrectObjectTypeException
			RevObject c = o;
			while (c instanceof RevTag) {
				c = ((RevTag) c).getObject();
				parseHeaders(c);
			}
			if (c instanceof Commit)
				((Commit) c).depth = 0;
			super.markStart(o);
		}

		public void markUnshallow(RevObject c) throws MissingObjectException
				IncorrectObjectTypeException
			if (c instanceof RevCommit)
				c.add(UNSHALLOW);
			super.markStart(c);
		}

		@Override
		protected RevCommit createCommit(AnyObjectId id) {
			return new Commit(id);
		}

		public int getDepth() {
			return depth;
		}

		public RevFlag getUnshallowFlag() {
			return UNSHALLOW;
		}

		public RevFlag getReinterestingFlag() {
			return REINTERESTING;
		}
	}
}
