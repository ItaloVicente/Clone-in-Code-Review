
package org.eclipse.jgit.internal.storage.reftable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.internal.storage.io.BlockSource;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Ref;

public abstract class Reftable implements AutoCloseable {
	public static Reftable from(Collection<Ref> refs) {
		try {
			ReftableConfig cfg = new ReftableConfig();
			cfg.setIndexObjects(false);
			ByteArrayOutputStream buf = new ByteArrayOutputStream();
			new ReftableWriter()
				.setConfig(cfg)
				.begin(buf)
				.sortAndWriteRefs(refs)
				.finish();
			return new ReftableReader(BlockSource.from(buf.toByteArray()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected boolean includeDeletes;

	public void setIncludeDeletes(boolean deletes) {
		includeDeletes = deletes;
	}

	public abstract RefCursor allRefs() throws IOException;

	public abstract RefCursor seek(String refName) throws IOException;

	public abstract RefCursor byObjectId(AnyObjectId id) throws IOException;

	public abstract LogCursor allLogs() throws IOException;

	public abstract LogCursor seekLog(String refName
			throws IOException;

	@Nullable
	public Ref exactRef(String refName) throws IOException {
		try (RefCursor rc = seek(refName)) {
			return rc.next() ? rc.getRef() : null;
		}
	}

	public boolean hasRef(String refName) throws IOException {
		try (RefCursor rc = seek(refName)) {
			return rc.next();
		}
	}

	public boolean hasId(AnyObjectId id) throws IOException {
		try (RefCursor rc = byObjectId(id)) {
			return rc.next();
		}
	}

	@Override
	public abstract void close() throws IOException;
}
