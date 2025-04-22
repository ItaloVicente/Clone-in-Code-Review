
package org.eclipse.jgit.storage.file;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import java.util.zip.ZipException;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.InflaterCache;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectStream;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.MutableInteger;
import org.eclipse.jgit.util.RawParseUtils;

public class UnpackedObject {
	private static final int BUFFER_SIZE = 8192;

	static final int LARGE_OBJECT = 1024 * 1024;

	public static ObjectLoader parse(byte[] raw
			throws IOException {
		WindowCursor wc = new WindowCursor(null);
		try {
			return open(new ByteArrayInputStream(raw)
		} finally {
			wc.release();
		}
	}

	static ObjectLoader open(InputStream in
			WindowCursor wc) throws IOException {
		try {
			in = buffer(in);
			in.mark(20);
			final byte[] hdr = new byte[64];
			IO.readFully(in

			if (isStandardFormat(hdr)) {
				in.reset();
				in = inflate(in
				int avail = readSome(in
				if (avail < 5)
					throw new CorruptObjectException(id
							JGitText.get().corruptObjectNoHeader);

				final MutableInteger p = new MutableInteger();
				int type = Constants.decodeTypeString(id
				long size = RawParseUtils.parseLongBase10(hdr
				if (size < 0)
					throw new CorruptObjectException(id
							JGitText.get().corruptObjectNegativeSize);
				if (hdr[p.value++] != 0)
					throw new CorruptObjectException(id
							JGitText.get().corruptObjectGarbageAfterSize);
				if (path == null && Integer.MAX_VALUE < size)
					throw new LargeObjectException(id.copy());
				if (size < LARGE_OBJECT || path == null) {
					byte[] data = new byte[(int) size];
					int n = avail - p.value;
					if (n > 0)
						System.arraycopy(hdr
					IO.readFully(in
					return new ObjectLoader.SmallObject(type
				}
				return new LargeObject(type

			} else {
				readSome(in
				int c = hdr[0] & 0xff;
				int type = (c >> 4) & 7;
				long size = c & 15;
				int shift = 4;
				int p = 1;
				while ((c & 0x80) != 0) {
					c = hdr[p++] & 0xff;
					size += (c & 0x7f) << shift;
					shift += 7;
				}

				switch (type) {
				case Constants.OBJ_COMMIT:
				case Constants.OBJ_TREE:
				case Constants.OBJ_BLOB:
				case Constants.OBJ_TAG:
					break;
				default:
					throw new CorruptObjectException(id
							JGitText.get().corruptObjectInvalidType);
				}

				if (path == null && Integer.MAX_VALUE < size)
					throw new LargeObjectException(id.copy());
				if (size < LARGE_OBJECT || path == null) {
					in.reset();
					IO.skipFully(in
					in = inflate(in
					byte[] data = new byte[(int) size];
					IO.readFully(in
					return new ObjectLoader.SmallObject(type
				}
				return new LargeObject(type
			}
		} catch (ZipException badStream) {
			throw new CorruptObjectException(id
					JGitText.get().corruptObjectBadStream);
		}
	}

	private static boolean isStandardFormat(final byte[] hdr) {
		final int fb = hdr[0] & 0xff;
		return fb == 0x78 && (((fb << 8) | hdr[1] & 0xff) % 31) == 0;
	}

	private static InputStream inflate(InputStream in
		final Inflater inf = InflaterCache.get();
		return new InflaterInputStream(in
			@Override
			public int read(byte[] b
				try {
					return super.read(b
				} catch (ZipException badStream) {
					throw new CorruptObjectException(id
							JGitText.get().corruptObjectBadStream);
				}
			}

			@Override
			public void close() throws IOException {
				super.close();
				InflaterCache.release(inf);
			}
		};
	}

	private static InputStream inflate(InputStream in
		return new InflaterInputStream(in
	}

	private static BufferedInputStream buffer(InputStream in) {
		return new BufferedInputStream(in
	}

	private static int readSome(InputStream in
			int cnt) throws IOException {
		int avail = 0;
		while (0 < cnt) {
			int n = in.read(hdr
			if (n < 0)
				break;
			avail += n;
			cnt -= n;
		}
		return avail;
	}

	private static final class LargeObject extends ObjectLoader {
		private final int type;

		private final long size;

		private final File path;

		private final ObjectId id;

		private final FileObjectDatabase source;

		private LargeObject(int type
				FileObjectDatabase db) {
			this.type = type;
			this.size = size;
			this.path = path;
			this.id = id.copy();
			this.source = db;
		}

		@Override
		public int getType() {
			return type;
		}

		@Override
		public long getSize() {
			return size;
		}

		@Override
		public boolean isLarge() {
			return true;
		}

		@Override
		public byte[] getCachedBytes() throws LargeObjectException {
			throw new LargeObjectException(id);
		}

		@Override
		public ObjectStream openStream() throws MissingObjectException
				IOException {
			InputStream in;
			try {
				in = buffer(new FileInputStream(path));
			} catch (FileNotFoundException gone) {
				return source.open(id
			}

			boolean ok = false;
			try {
				final byte[] hdr = new byte[64];
				in.mark(20);
				IO.readFully(in

				if (isStandardFormat(hdr)) {
					in.reset();
					in = buffer(inflate(in
					while (0 < in.read())
						continue;
				} else {
					readSome(in
					int c = hdr[0] & 0xff;
					int p = 1;
					while ((c & 0x80) != 0)
						c = hdr[p++] & 0xff;

					in.reset();
					IO.skipFully(in
					in = buffer(inflate(in
				}

				ok = true;
				return new ObjectStream.Filter(type
			} finally {
				if (!ok)
					in.close();
			}
		}
	}
}
