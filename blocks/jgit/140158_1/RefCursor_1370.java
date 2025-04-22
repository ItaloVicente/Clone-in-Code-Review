
package org.eclipse.jgit.internal.storage.reftable;

import java.io.IOException;
import java.util.List;
import java.util.PriorityQueue;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.ReflogEntry;

public class MergedReftable extends Reftable {
	private final Reftable[] tables;

	public MergedReftable(List<Reftable> tableStack) {
		tables = tableStack.toArray(new Reftable[0]);

		for (Reftable t : tables) {
			t.setIncludeDeletes(true);
		}
	}

	@Override
	public RefCursor allRefs() throws IOException {
		MergedRefCursor m = new MergedRefCursor();
		for (int i = 0; i < tables.length; i++) {
			m.add(new RefQueueEntry(tables[i].allRefs()
		}
		return m;
	}

	@Override
	public RefCursor seekRef(String name) throws IOException {
		MergedRefCursor m = new MergedRefCursor();
		for (int i = 0; i < tables.length; i++) {
			m.add(new RefQueueEntry(tables[i].seekRef(name)
		}
		return m;
	}

	@Override
	public RefCursor seekRefsWithPrefix(String prefix) throws IOException {
		MergedRefCursor m = new MergedRefCursor();
		for (int i = 0; i < tables.length; i++) {
			m.add(new RefQueueEntry(tables[i].seekRefsWithPrefix(prefix)
		}
		return m;
	}

	@Override
	public RefCursor byObjectId(AnyObjectId name) throws IOException {
		MergedRefCursor m = new MergedRefCursor();
		for (int i = 0; i < tables.length; i++) {
			m.add(new RefQueueEntry(tables[i].byObjectId(name)
		}
		return m;
	}

	@Override
	public LogCursor allLogs() throws IOException {
		MergedLogCursor m = new MergedLogCursor();
		for (int i = 0; i < tables.length; i++) {
			m.add(new LogQueueEntry(tables[i].allLogs()
		}
		return m;
	}

	@Override
	public LogCursor seekLog(String refName
			throws IOException {
		MergedLogCursor m = new MergedLogCursor();
		for (int i = 0; i < tables.length; i++) {
			m.add(new LogQueueEntry(tables[i].seekLog(refName
		}
		return m;
	}

	@Override
	public void close() throws IOException {
		for (Reftable t : tables) {
			t.close();
		}
	}

	int queueSize() {
		return Math.max(1
	}

	private class MergedRefCursor extends RefCursor {
		private final PriorityQueue<RefQueueEntry> queue;
		private RefQueueEntry head;
		private Ref ref;

		MergedRefCursor() {
			queue = new PriorityQueue<>(queueSize()
		}

		void add(RefQueueEntry t) throws IOException {
			if (!t.rc.next()) {
				t.rc.close();
			} else if (head == null) {
				RefQueueEntry p = queue.peek();
				if (p == null || RefQueueEntry.compare(t
					head = t;
				} else {
					head = queue.poll();
					queue.add(t);
				}
			} else if (RefQueueEntry.compare(t
				queue.add(t);
			} else {
				queue.add(head);
				head = t;
			}
		}

		@Override
		public boolean next() throws IOException {
			for (;;) {
				RefQueueEntry t = poll();
				if (t == null) {
					return false;
				}

				ref = t.rc.getRef();
				boolean include = includeDeletes || !t.rc.wasDeleted();
				add(t);
				skipShadowedRefs(ref.getName());
				if (include) {
					return true;
				}
			}
		}

		private RefQueueEntry poll() {
			RefQueueEntry e = head;
			if (e != null) {
				head = null;
				return e;
			}
			return queue.poll();
		}

		private void skipShadowedRefs(String name) throws IOException {
			for (;;) {
				RefQueueEntry t = head != null ? head : queue.peek();
				if (t != null && name.equals(t.name())) {
					add(poll());
				} else {
					break;
				}
			}
		}

		@Override
		public Ref getRef() {
			return ref;
		}

		@Override
		public void close() {
			if (head != null) {
				head.rc.close();
				head = null;
			}
			while (!queue.isEmpty()) {
				queue.remove().rc.close();
			}
		}
	}

	private static class RefQueueEntry {
		static int compare(RefQueueEntry a
			int cmp = a.name().compareTo(b.name());
			if (cmp == 0) {
				cmp = Long.signum(b.updateIndex() - a.updateIndex());
			}
			if (cmp == 0) {
				cmp = b.stackIdx - a.stackIdx;
			}
			return cmp;
		}

		final RefCursor rc;
		final int stackIdx;

		RefQueueEntry(RefCursor rc
			this.rc = rc;
			this.stackIdx = stackIdx;
		}

		String name() {
			return rc.getRef().getName();
		}

		long updateIndex() {
			return rc.getRef().getUpdateIndex();
		}
	}

	private class MergedLogCursor extends LogCursor {
		private final PriorityQueue<LogQueueEntry> queue;
		private String refName;
		private long updateIndex;
		private ReflogEntry entry;

		MergedLogCursor() {
			queue = new PriorityQueue<>(queueSize()
		}

		void add(LogQueueEntry t) throws IOException {
			if (t.lc.next()) {
				queue.add(t);
			} else {
				t.lc.close();
			}
		}

		@Override
		public boolean next() throws IOException {
			for (;;) {
				LogQueueEntry t = queue.poll();
				if (t == null) {
					return false;
				}

				refName = t.lc.getRefName();
				updateIndex = t.lc.getUpdateIndex();
				entry = t.lc.getReflogEntry();
				boolean include = includeDeletes || entry != null;
				skipShadowed(refName
				add(t);
				if (include) {
					return true;
				}
			}
		}

		private void skipShadowed(String name
			for (;;) {
				LogQueueEntry t = queue.peek();
				if (t != null && name.equals(t.name()) && index == t.index()) {
					add(queue.remove());
				} else {
					break;
				}
			}
		}

		@Override
		public String getRefName() {
			return refName;
		}

		@Override
		public long getUpdateIndex() {
			return updateIndex;
		}

		@Override
		public ReflogEntry getReflogEntry() {
			return entry;
		}

		@Override
		public void close() {
			while (!queue.isEmpty()) {
				queue.remove().lc.close();
			}
		}
	}

	private static class LogQueueEntry {
		static int compare(LogQueueEntry a
			int cmp = a.name().compareTo(b.name());
			if (cmp == 0) {
				cmp = Long.signum(b.index() - a.index());
			}
			if (cmp == 0) {
				cmp = b.stackIdx - a.stackIdx;
			}
			return cmp;
		}

		final LogCursor lc;
		final int stackIdx;

		LogQueueEntry(LogCursor lc
			this.lc = lc;
			this.stackIdx = stackIdx;
		}

		String name() {
			return lc.getRefName();
		}

		long index() {
			return lc.getUpdateIndex();
		}
	}
}
