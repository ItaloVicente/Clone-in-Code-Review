
package org.eclipse.jgit.revwalk;

import java.io.IOException;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.storage.pack.PackWriter;

public abstract class ObjectListIterator {
	private final ObjectWalk walk;

	protected ObjectListIterator(ObjectWalk walk) {
		this.walk = walk;
	}

	protected RevObject lookupAny(AnyObjectId id
		return walk.lookupAny(id
	}

	public abstract RevCommit next() throws IOException;

	public abstract RevObject nextObject() throws IOException;

	public abstract int getPathHashCode();

	public abstract void release();
}
