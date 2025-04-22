package org.eclipse.jgit.lfs.server;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.DigestOutputStream;
import java.text.MessageFormat;
import java.util.HashMap;

import org.eclipse.jgit.internal.storage.file.LockFile;
import org.eclipse.jgit.lfs.errors.CorruptLongObjectException;
import org.eclipse.jgit.lfs.lib.AnyLongObjectId;
import org.eclipse.jgit.lfs.lib.Constants;
import org.eclipse.jgit.lfs.lib.LongObjectId;
import org.eclipse.jgit.lfs.server.Response.Action;
import org.eclipse.jgit.lfs.server.internal.LfsServerText;

public class PlainFSRepository implements LargeFileRepository {

	private static class AtomicObjectOutputStream extends OutputStream {

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
			try {
				out.close();
				if (!aborted) {
					AnyLongObjectId contentHash = LongObjectId
							.fromRaw(out.getMessageDigest().digest());
					if (contentHash.equals(id)) {
						locked.commit();
					} else {
						abort();
						throw new CorruptLongObjectException(id
								MessageFormat.format(
										LfsServerText.get().corruptLongObject
										contentHash
					}
				}
			} finally {
				super.close();
			}
		}

		void abort() {
			locked.unlock();
			aborted = true;
		}
	}

	private final String url;
	private final Path dir;

	private AtomicObjectOutputStream out;


	public PlainFSRepository(String url
		this.url = url;
		this.dir = dir;
		Files.createDirectories(dir);
	}

	@Override
	public Response.Action getDownloadAction(AnyLongObjectId id) {
		return getAction(id);
	}

	@Override
	public Action getUploadAction(AnyLongObjectId id
		return getAction(id);
	}

	@Override
	public Action getVerifyAction(AnyLongObjectId id) {
		return null;
	}

	private Response.Action getAction(AnyLongObjectId id) {
		Response.Action a = new Response.Action();
		a.href = url + id.getName();
		a.header = new HashMap<>();
		a.header.put("Authorization"
		return a;
	}

	@Override
	public long getSize(AnyLongObjectId id) throws IOException {
		Path p = getPath(id);
		if (Files.exists(p)) {
			return Files.size(p);
		} else {
			return -1;
		}
	}

	public ReadableByteChannel getReadChannel(AnyLongObjectId id)
			throws IOException {
		return FileChannel.open(getPath(id)
	}

	public WritableByteChannel getWriteChannel(AnyLongObjectId id)
			throws IOException {
		Path path = getPath(id);
		Files.createDirectories(path.getParent());
		out = new AtomicObjectOutputStream(path
		return Channels.newChannel(out);
	}

	public void abortWrite() {
		if (out != null) {
			out.abort();
		}
	}

	public Path getDir() {
		return dir;
	}

	protected Path getPath(AnyLongObjectId id) {
		StringBuilder s = new StringBuilder(
				Constants.LONG_OBJECT_ID_STRING_LENGTH + 6);
		s.append(toHexCharArray(id.getFirstByte())).append('/');
		s.append(toHexCharArray(id.getSecondByte())).append('/');
		s.append(id.name());
		return dir.resolve(s.toString());
	}

	private char[] toHexCharArray(int b) {
		final char[] dst = new char[2];
		formatHexChar(dst
		return dst;
	}

	private static final char[] hexchar = { '0'
			'7'

	static void formatHexChar(final char[] dst
		int o = p + 1;
		while (o >= p && b != 0) {
			dst[o--] = hexchar[b & 0xf];
			b >>>= 4;
		}
		while (o >= p)
			dst[o--] = '0';
	}
}
