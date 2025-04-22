
package org.eclipse.jgit.revwalk;

abstract class AbstractRevQueue extends Generator {
	static final AbstractRevQueue EMPTY_QUEUE = new AlwaysEmptyQueue();

	int outputType;

	public abstract void add(RevCommit c);

	public final void add(RevCommit c
		if (!c.has(queueControl)) {
			c.add(queueControl);
			add(c);
		}
	}

	public final void addParents(RevCommit c
		final RevCommit[] pList = c.parents;
		if (pList == null)
			return;
		for (RevCommit p : pList)
			add(p
	}

	@Override
	public abstract RevCommit next();

	public abstract void clear();

	abstract boolean everbodyHasFlag(int f);

	abstract boolean anybodyHasFlag(int f);

	@Override
	int outputType() {
		return outputType;
	}

	protected static void describe(StringBuilder s
		s.append(c.toString());
		s.append('\n');
	}

	private static class AlwaysEmptyQueue extends AbstractRevQueue {
		@Override
		public void add(RevCommit c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public RevCommit next() {
			return null;
		}

		@Override
		boolean anybodyHasFlag(int f) {
			return false;
		}

		@Override
		boolean everbodyHasFlag(int f) {
			return true;
		}

		@Override
		public void clear() {
		}

	}
}
