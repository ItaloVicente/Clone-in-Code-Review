
package org.eclipse.jgit.internal.storage.reftable;

import static org.eclipse.jgit.lib.RefDatabase.MAX_SYMBOLIC_REF_DEPTH;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.internal.storage.io.BlockSource;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.SymbolicRef;

public abstract class Reftable implements AutoCloseable {
	public static Reftable from(Collection<Ref> refs) {
		try {
			ReftableConfig cfg = new ReftableConfig();
			cfg.setIndexObjects(false);
			cfg.setAlignBlocks(false);
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

	public abstract RefCursor seekRef(String refName) throws IOException;

	public abstract RefCursor seekRefsWithPrefix(String prefix) throws IOException;

	public abstract RefCursor byObjectId(AnyObjectId id) throws IOException;

	public abstract LogCursor allLogs() throws IOException;

	public LogCursor seekLog(String refName) throws IOException {
		return seekLog(refName
	}

	public abstract LogCursor seekLog(String refName
			throws IOException;

	@Nullable
	public Ref exactRef(String refName) throws IOException {
		try (RefCursor rc = seekRef(refName)) {
			return rc.next() ? rc.getRef() : null;
		}
	}

	public boolean hasRef(String refName) throws IOException {
		try (RefCursor rc = seekRef(refName)) {
			return rc.next();
		}
	}

	public boolean hasRefsWithPrefix(String prefix) throws IOException {
		try (RefCursor rc = seekRefsWithPrefix(prefix)) {
			return rc.next();
		}
	}

	public boolean hasId(AnyObjectId id) throws IOException {
		try (RefCursor rc = byObjectId(id)) {
			return rc.next();
		}
	}

	@Nullable
	public Ref resolve(Ref symref) throws IOException {
		return resolve(symref
	}

	private Ref resolve(Ref ref
		if (!ref.isSymbolic()) {
			return ref;
		}

		Ref dst = ref.getTarget();
		if (MAX_SYMBOLIC_REF_DEPTH <= depth) {
		}

		dst = exactRef(dst.getName());
		if (dst == null) {
			return ref;
		}

		dst = resolve(dst
		if (dst == null) {
		}
		return new SymbolicRef(ref.getName()
	}

	@Override
	public abstract void close() throws IOException;
}
