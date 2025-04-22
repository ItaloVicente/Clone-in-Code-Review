
package org.eclipse.jgit.internal.storage.reftable;

import java.io.IOException;

import org.eclipse.jgit.lib.Ref;

public abstract class RefCursor implements AutoCloseable {
	public abstract boolean next() throws IOException;

	public abstract Ref getRef();

	public boolean wasDeleted() {
		Ref r = getRef();
		return r.getStorage() == Ref.Storage.NEW && r.getObjectId() == null;
	}

	@Override
	public abstract void close();
}
