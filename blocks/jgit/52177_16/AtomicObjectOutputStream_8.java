package org.eclipse.jgit.lfs.server.fs;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.security.DigestOutputStream;
import java.text.MessageFormat;

import org.eclipse.jgit.internal.storage.file.LockFile;
import org.eclipse.jgit.lfs.errors.CorruptLongObjectException;
import org.eclipse.jgit.lfs.lib.AnyLongObjectId;
import org.eclipse.jgit.lfs.lib.Constants;
import org.eclipse.jgit.lfs.lib.LongObjectId;
import org.eclipse.jgit.lfs.server.internal.LfsServerText;

class AtomicObjectOutputStream extends OutputStream {

	private LockFile locked;

	private DigestOutputStream out;

	private boolean aborted;

	private AnyLongObjectId id;

	AtomicObjectOutputStream(Path path
			throws IOException {
		locked = new LockFile(path.toFile());
		locked.lock();
		this.id = id;
		out = new DigestOutputStream(locked.getOutputStream()
				Constants.newMessageDigest());
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
			verifyHash();
			locked.commit();
		}
	}

	private void verifyHash() {
		AnyLongObjectId contentHash = LongObjectId
				.fromRaw(out.getMessageDigest().digest());
		if (!contentHash.equals(id)) {
			abort();
			throw new CorruptLongObjectException(id
					MessageFormat.format(LfsServerText.get().corruptLongObject
							contentHash
		}
	}

	void abort() {
		locked.unlock();
		aborted = true;
	}
}
