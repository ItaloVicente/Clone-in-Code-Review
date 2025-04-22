
package org.eclipse.jgit.internal.ketch;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;

class LogIndex extends ObjectId {
	static LogIndex unknown(AnyObjectId id) {
		return new LogIndex(id
	}

	private final long index;

	private LogIndex(AnyObjectId id
		super(id);
		this.index = index;
	}

	LogIndex nextIndex(AnyObjectId id) {
		return new LogIndex(id
	}

	boolean isBefore(LogIndex c) {
		return index <= c.index;
	}

	@SuppressWarnings("boxing")
	String describeForLog() {
		return String.format("%5d/%s"
	}

	@SuppressWarnings("boxing")
	@Override
	public String toString() {
		return String.format("LogId[%d %s]"
	}
}
