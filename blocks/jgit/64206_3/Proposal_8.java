
package org.eclipse.jgit.internal.ketch;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;

class LogId extends ObjectId {
	static LogId unknown(AnyObjectId id) {
		return new LogId(id
	}

	private final long index;

	private LogId(AnyObjectId id
		super(id);
		this.index = index;
	}

	LogId nextId(AnyObjectId id) {
		return new LogId(id
	}

	boolean isBefore(LogId c) {
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
