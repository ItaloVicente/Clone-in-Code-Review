
package org.eclipse.jgit.internal.storage.file;

import java.util.concurrent.atomic.AtomicReferenceArray;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;

class UnpackedObjectCache {


	private volatile Table table;

	UnpackedObjectCache() {
		table = new Table(INITIAL_BITS);
	}

	boolean isUnpacked(AnyObjectId objectId) {
		return table.contains(objectId);
	}

	void add(AnyObjectId objectId) {
		Table t = table;
		if (t.add(objectId)) {
		} else {
			Table n = new Table(Math.min(t.bits + 1
			n.add(objectId);
			table = n;
		}
	}

	void remove(AnyObjectId objectId) {
		if (isUnpacked(objectId))
			clear();
	}

	void clear() {
		table = new Table(INITIAL_BITS);
	}

	private static class Table {
		private static final int MAX_CHAIN = 8;

		private final AtomicReferenceArray<ObjectId> ids;

		private final int shift;

		final int bits;

		Table(int bits) {
			this.ids = new AtomicReferenceArray<>(1 << bits);
			this.shift = 32 - bits;
			this.bits = bits;
		}

		boolean contains(AnyObjectId toFind) {
			int i = index(toFind);
			for (int n = 0; n < MAX_CHAIN; n++) {
				ObjectId obj = ids.get(i);
				if (obj == null)
					break;

				if (AnyObjectId.equals(obj
					return true;

				if (++i == ids.length())
					i = 0;
			}
			return false;
		}

		boolean add(AnyObjectId toAdd) {
			int i = index(toAdd);
			for (int n = 0; n < MAX_CHAIN;) {
				ObjectId obj = ids.get(i);
				if (obj == null) {
					if (ids.compareAndSet(i
						return true;
					else
						continue;
				}

				if (AnyObjectId.equals(obj
					return true;

				if (++i == ids.length())
					i = 0;
				n++;
			}
			return false;
		}

		private int index(AnyObjectId id) {
			return id.hashCode() >>> shift;
		}
	}
}
