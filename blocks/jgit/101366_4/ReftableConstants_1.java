
package org.eclipse.jgit.internal.storage.dfs;

import static org.eclipse.jgit.internal.storage.pack.PackExt.REFTABLE;
import static org.eclipse.jgit.internal.storage.reftable.ReftableConstants.FILE_HEADER_LEN;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.eclipse.jgit.internal.storage.io.BlockSource;
import org.eclipse.jgit.internal.storage.reftable.RefCursor;
import org.eclipse.jgit.internal.storage.reftable.ReftableReader;
import org.eclipse.jgit.util.NB;

public class DfsReftable extends BlockBasedFile {
	DfsReftable(DfsBlockCache cache
		super(cache
		length = desc.getFileSize(REFTABLE);
		if (length <= 0) {
			length = -1;
		}
	}

	public RefCursor open(DfsReader ctx) throws IOException {
		return new ReftableReader(new CacheSource(this
	}

	private static final class CacheSource extends BlockSource {
		private final DfsReftable file;
		private final DfsStreamKey key;
		private final DfsBlockCache cache;
		private final DfsReader ctx;
		private ReadableChannel ch;

		CacheSource(DfsReftable file
				DfsBlockCache cache
			this.file = file;
			this.key = key;
			this.cache = cache;
			this.ctx = ctx;
		}

		@Override
		public ByteBuffer read(long pos
			if (want == 0) {
				return ByteBuffer.allocate(0);
			}

			int bs = file.blockSize;
			DfsBlock block = cache.get(key
			if (block != null && isAlignedToRequest(block
				if (bs == 0 && pos == 0) {
					file.setBlockSize(readBlockSize(block));
				}
				return block.zeroCopyByteBuffer(want);
			}

			if (bs == 0) {
				bs = readBlockSize();
				file.setBlockSize(bs);
			}

			block = cache.getOrLoad(file
			if (isAlignedToRequest(block
				return block.zeroCopyByteBuffer(want);
			}

			byte[] dst = new byte[want];
			int off = 0;
			for (;;) {
				int r = block.copy(pos
				pos += r;
				off += r;
				want -= r;
				if (want == 0) {
					return ByteBuffer.wrap(dst);
				}
				block = cache.getOrLoad(file
			}
		}

		private static boolean isAlignedToRequest(DfsBlock b
			return b.start == p && b.end >= p + n;
		}

		private int readBlockSize() throws IOException {
			int bs = open().blockSize();
			if (bs <= 0) {
				byte[] tmp = new byte[FILE_HEADER_LEN];
				BlockBasedFile.read(ch
				bs = NB.decodeInt32(tmp
			}
			return bs;
		}

		private int readBlockSize(DfsBlock block) throws IOException {
			byte[] tmp = new byte[4];
			block.copy(4
			return NB.decodeInt32(tmp
		}

		@Override
		public long size() throws IOException {
			long n = file.length;
			if (n < 0) {
				n = open().size();
				file.length = n;
			}
			return n;
		}

		@Override
		public void adviseSequentialRead(long start
			int sz = ctx.getOptions().getStreamPackBufferSize();
			if (sz > 0) {
				try {
					ch.setReadAheadBytes((int) Math.min(end - start
				} catch (IOException e) {
				}
			}
		}

		private ReadableChannel open() throws IOException {
			if (ch == null) {
				ch = ctx.db.openFile(file.desc
			}
			return ch;
		}

		@Override
		public void close() {
			if (ch != null) {
				try {
					ch.close();
				} catch (IOException e) {
				} finally {
					ch = null;
				}
			}
		}
	}
}
