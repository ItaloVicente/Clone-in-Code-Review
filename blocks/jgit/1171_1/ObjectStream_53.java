
package org.eclipse.jgit.lib;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.storage.pack.ObjectReuseAsIs;

public abstract class ObjectReader {
	protected static final int OBJ_ANY = -1;

	public abstract ObjectReader newReader();

	public boolean has(AnyObjectId objectId) throws IOException {
		return has(objectId
	}

	public boolean has(AnyObjectId objectId
		try {
			open(objectId
			return true;
		} catch (MissingObjectException notFound) {
			return false;
		}
	}

	public ObjectLoader open(AnyObjectId objectId)
			throws MissingObjectException
		return open(objectId
	}

	public abstract ObjectLoader open(AnyObjectId objectId
			throws MissingObjectException
			IOException;

	public long getObjectSize(AnyObjectId objectId
			throws MissingObjectException
			IOException {
		return open(objectId
	}

	public void release() {
	}
}
