package org.eclipse.jgit.lfs.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.security.DigestOutputStream;
import java.text.MessageFormat;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.internal.storage.file.LockFile;
import org.eclipse.jgit.lfs.errors.CorruptLongObjectException;
import org.eclipse.jgit.lfs.lib.AnyLongObjectId;
import org.eclipse.jgit.lfs.lib.Constants;
import org.eclipse.jgit.lfs.lib.LongObjectId;

public class AtomicObjectOutputStream extends OutputStream {

	private LockFile locked;

	private DigestOutputStream out;

	private boolean aborted;

	private AnyLongObjectId id;

	public AtomicObjectOutputStream(Path path
			throws IOException {
		locked = new LockFile(path.toFile());
		locked.lock();
		this.id = id;
		out = new DigestOutputStream(locked.getOutputStream()
				Constants.newMessageDigest());
	}

	public AtomicObjectOutputStream(Path path) throws IOException {
		this(path
	}

	@Nullable
	public AnyLongObjectId getId() {
		return id;
	}

	@Override
	public void write(int b) throws IOException {
		out.write(b);
	}

	@Override
	public void write(byte[] b) throws IOException {
		out.write(b);
	}

	@Override
	public void write(byte[] b
		out.write(b
	}

	@Override
	public void close() throws IOException {
		out.close();
		if (!aborted) {
			if (id != null) {
				verifyHash();
			} else {
				id = LongObjectId.fromRaw(out.getMessageDigest().digest());
			}
			locked.commit();
		}
	}

	private void verifyHash() {
		AnyLongObjectId contentHash = LongObjectId
				.fromRaw(out.getMessageDigest().digest());
		if (!contentHash.equals(id)) {
			abort();
			throw new CorruptLongObjectException(id
					MessageFormat.format(LfsText.get().corruptLongObject
							contentHash
		}
	}

	public void abort() {
		locked.unlock();
		aborted = true;
	}
}
