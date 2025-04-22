
package org.eclipse.jgit.internal.ketch;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;

class LogId extends ObjectId {
	final long index;

	LogId(AnyObjectId id
		super(id);
		this.index = index;
	}

	@SuppressWarnings("boxing")
	@Override
	public String toString() {
		return String.format("LogId[%d %s]"
	}
}
