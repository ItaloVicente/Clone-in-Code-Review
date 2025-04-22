
package org.eclipse.jgit.internal.storage.dfs;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.MessageFormat;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.errors.PackInvalidException;
import org.eclipse.jgit.internal.storage.pack.PackExt;

public abstract class BlockBasedFile {
	final DfsBlockCache cache;

	final DfsStreamKey key;

	final DfsPackDescription packDesc;
	final PackExt ext;

	volatile int blockSize;

	volatile long length;

	volatile boolean invalid;

	BlockBasedFile(DfsBlockCache cache
			DfsPackDescription packDesc
		this.cache = cache;
		this.key = key;
		this.packDesc = packDesc;
		this.ext = ext;
	}

	String getFileName() {
		return packDesc.getFileName(ext);
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

	DfsBlock readOneBlock(long pos
			@Nullable ReadableChannel fileChannel) throws IOException {
		if (invalid)
			throw new PackInvalidException(getFileName());

		ctx.stats.readBlock++;
		long start = System.nanoTime();
		ReadableChannel rc = fileChannel != null ? fileChannel
				: ctx.db.openFile(packDesc
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
			if (rc != fileChannel) {
				rc.close();
			}
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
}
