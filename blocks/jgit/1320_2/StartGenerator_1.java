
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

	public class Commit extends RevCommit {

		public int depth;

		protected Commit(AnyObjectId id) {
			super(id);
			depth = -1;
		}
	}

	public class RevWalk extends org.eclipse.jgit.revwalk.RevWalk implements DepthWalk {
		private final int depth;
		private final RevFlag UNSHALLOW;

		public RevWalk(Repository repo
			super(repo);

			this.depth = depth;
			this.UNSHALLOW = newFlag("UNSHALLOW");
		}

		public RevWalk(ObjectReader or
			super(or);

			this.depth = depth;
			this.UNSHALLOW = newFlag("UNSHALLOW");
		}

		@Override
		protected RevCommit createCommit(final AnyObjectId id) {
			return new Commit(id);
		}

		public int getDepth() {
			return depth;
		}

		public RevFlag getUnshallowFlag() {
			return UNSHALLOW;
		}
	}

	public class ObjectWalk extends org.eclipse.jgit.revwalk.ObjectWalk implements DepthWalk {
		private final int depth;
		private final RevFlag UNSHALLOW;

		public ObjectWalk(Repository repo
			super(repo);

			this.depth = depth;
			this.UNSHALLOW = newFlag("UNSHALLOW");
		}

		public ObjectWalk(ObjectReader or
			super(or);

			this.depth = depth;
			this.UNSHALLOW = newFlag("UNSHALLOW");
		}

		public void markUnshallow(RevObject c) throws MissingObjectException
		IncorrectObjectTypeException
			if (c instanceof RevCommit) {
				c.add(UNSHALLOW);
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

		public RevFlag getUnshallowFlag() {
			return UNSHALLOW;
		}
	}
}
