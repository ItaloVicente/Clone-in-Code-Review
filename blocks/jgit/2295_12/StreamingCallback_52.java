
package org.eclipse.jgit.storage.dht;

import java.io.IOException;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.AsyncObjectSizeQueue;
import org.eclipse.jgit.lib.ObjectId;

final class SizeQueue<T extends ObjectId> extends QueueObjectLookup<T>
		implements AsyncObjectSizeQueue<T> {
	private ObjectWithInfo<T> currResult;

	SizeQueue(DhtReader reader
		super(reader
	}

	public boolean next() throws MissingObjectException
		currResult = nextObjectWithInfo();
		return currResult != null;
	}

	public T getCurrent() {
		return currResult.object;
	}

	public long getSize() {
		return currResult.info.getSize();
	}

	public ObjectId getObjectId() {
		return getCurrent();
	}
}
