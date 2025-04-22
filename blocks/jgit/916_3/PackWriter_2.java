
package org.eclipse.jgit.lib;

import java.io.IOException;

import org.eclipse.jgit.errors.MissingObjectException;

public abstract class ObjectReader {
	protected static final int OBJ_ANY = -1;

	public boolean hasObject(AnyObjectId objectId) throws IOException {
		try {
			openObject(objectId);
			return true;
		} catch (MissingObjectException notFound) {
			return false;
		}
	}

	public ObjectLoader openObject(AnyObjectId objectId)
			throws MissingObjectException
		return openObject(objectId
	}

	public abstract ObjectLoader openObject(AnyObjectId objectId
			throws MissingObjectException

	public void release() {
	}
}
