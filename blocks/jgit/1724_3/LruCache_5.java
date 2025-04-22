
package org.eclipse.jgit.util.io;

import java.io.IOException;

import org.eclipse.jgit.storage.pack.DeltaStream;
import org.eclipse.jgit.util.IO;

public class CachingSeekableInputStream extends SeekableInputStream {
	private final SeekableInputStream in;

	private final int blockSize;

	private final LruCache<byte[]> cache;

	private long sz = -1;

	private long ptr;

	public CachingSeekableInputStream(SeekableInputStream in
			int cacheSize) {
		this.in = in;
		this.blockSize = blockSize;
		this.cache = new LruCache<byte[]>(cacheSize / blockSize);
	}

	@Override
	public void seek(long offset) throws IOException {
		ptr = offset;
	}

	@Override
	public long size() throws IOException {
		if (sz < 0)
			sz = in.size();
		return sz;
	}

	@Override
	public long position() {
		return ptr;
	}

	@Override
	public int read(byte[] b
		if (len == 0)
			return 0;
		if (ptr == size())
			return -1;

		int r = 0;
		while (0 < len) {
			final long blockId = ptr / blockSize;
			final long blockStart = blockId * blockSize;
			final int p = (int) (ptr - blockStart);

			byte[] block = cache.get(blockId);
			if (block == null) {
				in.seek(blockStart);

				if (p == 0 && blockSize <= len) {
					final int n = blockSize;
					IO.readFully(in
					off += n;
					len -= n;
					r += n;
					ptr += n;
					continue;
				}

				block = new byte[(int) Math.min(blockSize
				IO.readFully(in
				cache.put(blockId
			}

			final int n = Math.min(len
			System.arraycopy(block
			off += n;
			len -= n;
			r += n;
			ptr += n;
		}
		return r;
	}

	@Override
	public void close() throws IOException {
		in.close();
	}
}
