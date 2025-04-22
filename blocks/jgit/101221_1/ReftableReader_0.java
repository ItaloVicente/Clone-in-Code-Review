
package org.eclipse.jgit.internal.storage.reftable;

import java.io.IOException;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.lib.Ref;

public abstract class RefCursor implements AutoCloseable {
	public abstract void seekToFirstRef() throws IOException;

	public abstract void seek(String refName) throws IOException;

	public abstract boolean next() throws IOException;

	public abstract Ref getRef();

	public boolean wasDeleted() {
		Ref r = getRef();
		return r.getStorage() == Ref.Storage.NEW && r.getObjectId() == null;
	}

	@Nullable
	public Ref exactRef(String refName) throws IOException {
		seek(refName);
		return next() ? getRef() : null;
	}

	public boolean hasRef(String refName) throws IOException {
		seek(refName);
		return next();
	}

	@Override
	public abstract void close() throws IOException;
}
