
package org.eclipse.jgit.internal.storage.reftable;

import java.io.IOException;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.ReflogEntry;

public abstract class RefCursor implements AutoCloseable {
	protected boolean includeDeletes;

	public void setIncludeDeletes(boolean deletes) {
		includeDeletes = deletes;
	}

	public abstract void seekToFirstRef() throws IOException;

	public abstract void seek(String refName) throws IOException;

	public abstract void seekToFirstLog() throws IOException;

	public abstract void seekLog(String refName

	public abstract boolean next() throws IOException;

	public abstract String getRefName();

	public abstract Ref getRef();

	public boolean wasDeleted() {
		Ref r = getRef();
		return r.getStorage() == Ref.Storage.NEW && r.getObjectId() == null;
	}

	public abstract ReflogEntry getReflogEntry();

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
