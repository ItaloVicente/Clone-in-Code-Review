
package org.eclipse.jgit.internal.storage.pack;

final class DeltaWindowEntry {
	DeltaWindowEntry prev;
	DeltaWindowEntry next;
	ObjectToPack object;

	byte[] buffer;

	DeltaIndex index;

	final void set(ObjectToPack object) {
		this.object = object;
		this.index = null;
		this.buffer = null;
	}

	final int depth() {
		return object.getDeltaDepth();
	}

	final int type() {
		return object.getType();
	}

	final int size() {
		return object.getWeight();
	}

	final boolean empty() {
		return object == null;
	}

	final void makeNext(DeltaWindowEntry e) {
		e.prev.next = e.next;
		e.next.prev = e.prev;

		e.next = next;
		e.prev = this;
		next.prev = e;
		next = e;
	}

	static DeltaWindowEntry createWindow(int cnt) {
		DeltaWindowEntry res = new DeltaWindowEntry();
		DeltaWindowEntry p = res;
		for (int i = 0; i < cnt; i++) {
			DeltaWindowEntry e = new DeltaWindowEntry();
			e.prev = p;
			p.next = e;
			p = e;
		}
		p.next = res;
		res.prev = p;
		return res;
	}
}
