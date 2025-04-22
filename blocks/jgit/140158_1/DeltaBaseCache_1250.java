
package org.eclipse.jgit.internal.storage.dfs;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.MessageFormat;

import org.eclipse.jgit.errors.PackInvalidException;
import org.eclipse.jgit.internal.storage.pack.PackExt;

abstract class BlockBasedFile {
	final DfsBlockCache cache;

	final DfsStreamKey key;

	final DfsPackDescription desc;
	final PackExt ext;

	volatile int blockSize;

	volatile long length;

	volatile boolean invalid;

	BlockBasedFile(DfsBlockCache cache
		this.cache = cache;
		this.key = desc.getStreamKey(ext);
		this.desc = desc;
		this.ext = ext;
	}

	String getFileName() {
		return desc.getFileName(ext);
	}

	boolean invalid() {
		return invalid;
	}

	void setInvalid() {
		invalid = true;
	}

	void setBlockSize(int newSize) {
		blockSize = newSize;
	}

	long alignToBlock(long pos) {
		int size = blockSize;
		if (size == 0)
			size = cache.getBlockSize();
		return (pos / size) * size;
	}

	int blockSize(ReadableChannel rc) {
		int size = blockSize;
		if (size == 0) {
			size = rc.blockSize();
			if (size <= 0)
				size = cache.getBlockSize();
			else if (size < cache.getBlockSize())
				size = (cache.getBlockSize() / size) * size;
			blockSize = size;
		}
		return size;
	}

	DfsBlock getOrLoadBlock(long pos
		try (LazyChannel c = new LazyChannel(ctx
			return cache.getOrLoad(this
		}
	}

	DfsBlock readOneBlock(long pos
			throws IOException {
		if (invalid)
			throw new PackInvalidException(getFileName());

		ctx.stats.readBlock++;
		long start = System.nanoTime();
		try {
			int size = blockSize(rc);
			pos = (pos / size) * size;

			long len = length;
			if (len < 0) {
				len = rc.size();
				if (0 <= len)
					length = len;
			}

			if (0 <= len && len < pos + size)
				size = (int) (len - pos);
			if (size <= 0)
				throw new EOFException(MessageFormat.format(
						DfsText.get().shortReadOfBlock
						getFileName()

			byte[] buf = new byte[size];
			rc.position(pos);
			int cnt = read(rc
			ctx.stats.readBlockBytes += cnt;
			if (cnt != size) {
				if (0 <= len) {
					throw new EOFException(MessageFormat.format(
							DfsText.get().shortReadOfBlock
							getFileName()
							Integer.valueOf(cnt)));
				}

				byte[] n = new byte[cnt];
				System.arraycopy(buf
				buf = n;
			} else if (len < 0) {
				length = len = rc.size();
			}

			return new DfsBlock(key
		} finally {
			ctx.stats.readBlockMicros += elapsedMicros(start);
		}
	}

	static int read(ReadableChannel rc
		int n;
		do {
			n = rc.read(buf);
		} while (0 < n && buf.hasRemaining());
		return buf.position();
	}

	static long elapsedMicros(long start) {
		return (System.nanoTime() - start) / 1000L;
	}

	private static class LazyChannel
			implements AutoCloseable
		private final DfsReader ctx;
		private final DfsPackDescription desc;
		private final PackExt ext;

		private ReadableChannel rc;

		LazyChannel(DfsReader ctx
			this.ctx = ctx;
			this.desc = desc;
			this.ext = ext;
		}

		@Override
		public ReadableChannel get() throws IOException {
			if (rc == null) {
				rc = ctx.db.openFile(desc
			}
			return rc;
		}

		@Override
		public void close() throws IOException {
			if (rc != null) {
				rc.close();
			}
		}
	}
}
