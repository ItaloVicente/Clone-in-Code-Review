
package org.eclipse.jgit.revwalk;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;

public interface DepthWalk {
	int getDepth();

	default int getDeepenSince() {
		return 0;
	}

	default List<ObjectId> getDeepenNots() {
		return Collections.emptyList();
	}

	RevFlag getUnshallowFlag();

	RevFlag getReinterestingFlag();

	RevFlag getDeepenNotFlag();

	public static class Commit extends RevCommit {
		int depth;

		boolean isBoundary;

		boolean makesChildBoundary;

		public int getDepth() {
			return depth;
		}

		public boolean isBoundary() {
			return isBoundary;
		}

		protected Commit(AnyObjectId id) {
			super(id);
			depth = -1;
		}
	}

	public class RevWalk extends org.eclipse.jgit.revwalk.RevWalk implements DepthWalk {
		private final int depth;

		private int deepenSince;

		private List<ObjectId> deepenNots;

		private final RevFlag UNSHALLOW;

		private final RevFlag REINTERESTING;

		private final RevFlag DEEPEN_NOT;

		public RevWalk(Repository repo
			super(repo);

			this.depth = depth;
			this.deepenNots = Collections.emptyList();
		}

		public RevWalk(ObjectReader or
			super(or);

			this.depth = depth;
			this.deepenNots = Collections.emptyList();
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

		@Override
		public int getDepth() {
			return depth;
		}

		@Override
		public int getDeepenSince() {
			return deepenSince;
		}

		public void setDeepenSince(int limit) {
			deepenSince = limit;
		}

		@Override
		public List<ObjectId> getDeepenNots() {
			return deepenNots;
		}

		public void setDeepenNots(List<ObjectId> deepenNots) {
			this.deepenNots = Objects.requireNonNull(deepenNots);
		}

		@Override
		public RevFlag getUnshallowFlag() {
			return UNSHALLOW;
		}

		@Override
		public RevFlag getReinterestingFlag() {
			return REINTERESTING;
		}

		@Override
		public RevFlag getDeepenNotFlag() {
			return DEEPEN_NOT;
		}

		@Override
		public ObjectWalk toObjectWalkWithSameObjects() {
			ObjectWalk ow = new ObjectWalk(reader
			ow.deepenSince = deepenSince;
			ow.deepenNots = deepenNots;
			ow.objects = objects;
			ow.freeFlags = freeFlags;
			return ow;
		}
	}

	public class ObjectWalk extends org.eclipse.jgit.revwalk.ObjectWalk implements DepthWalk {
		private final int depth;

		private int deepenSince;

		private List<ObjectId> deepenNots;

		private final RevFlag UNSHALLOW;

		private final RevFlag REINTERESTING;

		private final RevFlag DEEPEN_NOT;

		public ObjectWalk(Repository repo
			super(repo);

			this.depth = depth;
			this.deepenNots = Collections.emptyList();
		}

		public ObjectWalk(ObjectReader or
			super(or);

			this.depth = depth;
			this.deepenNots = Collections.emptyList();
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

		@Override
		public int getDepth() {
			return depth;
		}

		@Override
		public int getDeepenSince() {
			return deepenSince;
		}

		@Override
		public List<ObjectId> getDeepenNots() {
			return deepenNots;
		}

		@Override
		public RevFlag getUnshallowFlag() {
			return UNSHALLOW;
		}

		@Override
		public RevFlag getReinterestingFlag() {
			return REINTERESTING;
		}

		@Override
		public RevFlag getDeepenNotFlag() {
			return DEEPEN_NOT;
		}
	}
}
