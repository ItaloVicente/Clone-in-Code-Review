
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
			DfsPackDescription desc) {
		super(cache
		length = desc.getFileSize(REFTABLE);
		if (length <= 0) {
			length = 1;
		}
	}

	public RefCursor open(DfsReader ctx) throws IOException {
		return new ReftableReader(new CacheSource(ctx));
	}

	private final class CacheSource extends BlockSource {
		private final DfsReader ctx;
		private ReadableChannel ch;

		CacheSource(DfsReader ctx) {
			this.ctx = ctx;
		}

		@Override
		public ByteBuffer read(long pos
			DfsBlock block = cache.get(key
			if (block != null && isAlignedToRead(block
				return zeroCopyBlock(block
			}

			if (pos == 0 && bs == FILE_HEADER_LEN) {
				bs = readBlockSize(pos
				blockSize = bs;
			}

			block = cache.getOrLoad(DfsReftable.this
			if (isAlignedToRead(block
				return zeroCopyBlock(block
			}

			byte[] dst = new byte[bs];
			int n = 0;
			while (n < bs) {
				n += block.copy(pos
				block = cache.getOrLoad(DfsReftable.this
			}
			return ByteBuffer.wrap(dst);
		}

		private boolean isAlignedToRead(DfsBlock block
			return block.start == pos && block.end >= pos + bs;
		}

		private ByteBuffer zeroCopyBlock(DfsBlock block
			ByteBuffer b = block.asByteBuffer();
			b.limit(bs);
			return b;
		}

		private int readBlockSize(long pos
			byte[] tmp = new byte[bs];
			ByteBuffer b = ByteBuffer.wrap(tmp);
			open();
			ch.position(pos);
			int n;
			do {
				n = ch.read(b);
			} while (n > 0 && b.position() < bs);
			return NB.decodeInt32(tmp
		}

		@Override
		public long size() throws IOException {
			if (length < 0) {
				length = open().size();
			}
			return length;
		}

		private ReadableChannel open() throws IOException {
			if (ch == null) {
				ch = ctx.db.openFile(packDesc
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
