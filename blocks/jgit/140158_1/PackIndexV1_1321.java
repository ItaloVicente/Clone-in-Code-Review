
package org.eclipse.jgit.internal.storage.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.UnsupportedPackIndexVersionException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdSet;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.NB;
import org.eclipse.jgit.util.io.SilentFileInputStream;

public abstract class PackIndex
		implements Iterable<PackIndex.MutableEntry>
	public static PackIndex open(File idxFile) throws IOException {
		try (SilentFileInputStream fd = new SilentFileInputStream(
				idxFile)) {
				return read(fd);
		} catch (IOException ioe) {
			throw new IOException(
					MessageFormat.format(JGitText.get().unreadablePackIndex
							idxFile.getAbsolutePath())
					ioe);
		}
	}

	public static PackIndex read(InputStream fd) throws IOException
			CorruptObjectException {
		final byte[] hdr = new byte[8];
		IO.readFully(fd
		if (isTOC(hdr)) {
			final int v = NB.decodeInt32(hdr
			switch (v) {
			case 2:
				return new PackIndexV2(fd);
			default:
				throw new UnsupportedPackIndexVersionException(v);
			}
		}
		return new PackIndexV1(fd
	}

	private static boolean isTOC(byte[] h) {
		final byte[] toc = PackIndexWriter.TOC;
		for (int i = 0; i < toc.length; i++)
			if (h[i] != toc[i])
				return false;
		return true;
	}

	protected byte[] packChecksum;

	public boolean hasObject(AnyObjectId id) {
		return findOffset(id) != -1;
	}

	@Override
	public boolean contains(AnyObjectId id) {
		return findOffset(id) != -1;
	}

	@Override
	public abstract Iterator<MutableEntry> iterator();

	public abstract long getObjectCount();

	public abstract long getOffset64Count();

	public abstract ObjectId getObjectId(long nthPosition);

	public final ObjectId getObjectId(int nthPosition) {
		if (nthPosition >= 0)
			return getObjectId((long) nthPosition);
		final int u31 = nthPosition >>> 1;
		final int one = nthPosition & 1;
		return getObjectId(((long) u31) << 1 | one);
	}

	abstract long getOffset(long nthPosition);

	public abstract long findOffset(AnyObjectId objId);

	public abstract long findCRC32(AnyObjectId objId)
			throws MissingObjectException

	public abstract boolean hasCRC32Support();

	public abstract void resolve(Set<ObjectId> matches
			int matchLimit) throws IOException;

	public static class MutableEntry {
		final MutableObjectId idBuffer = new MutableObjectId();

		long offset;

		public long getOffset() {
			return offset;
		}

		public String name() {
			ensureId();
			return idBuffer.name();
		}

		public ObjectId toObjectId() {
			ensureId();
			return idBuffer.toObjectId();
		}

		public MutableEntry cloneEntry() {
			final MutableEntry r = new MutableEntry();
			ensureId();
			r.idBuffer.fromObjectId(idBuffer);
			r.offset = offset;
			return r;
		}

		void ensureId() {
		}
	}

	abstract class EntriesIterator implements Iterator<MutableEntry> {
		protected final MutableEntry entry = initEntry();

		protected long returnedNumber = 0;

		protected abstract MutableEntry initEntry();

		@Override
		public boolean hasNext() {
			return returnedNumber < getObjectCount();
		}

		@Override
		public abstract MutableEntry next();

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
