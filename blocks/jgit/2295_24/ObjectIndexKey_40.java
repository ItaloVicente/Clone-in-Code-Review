
package org.eclipse.jgit.storage.dht;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;

import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectStream;

class LargeNonDeltaObject extends ObjectLoader {
	private final int type;

	private final long sz;

	private final int pos;

	private final DhtReader ctx;

	private final ChunkMeta meta;

	private PackChunk firstChunk;

	LargeNonDeltaObject(int type
		this.type = type;
		this.sz = sz;
		this.pos = pos;
		this.ctx = ctx;
		this.meta = pc.getMeta();
		firstChunk = pc;
	}

	@Override
	public boolean isLarge() {
		return true;
	}

	@Override
	public byte[] getCachedBytes() throws LargeObjectException {
		throw new LargeObjectException.ExceedsByteArrayLimit();
	}

	@Override
	public int getType() {
		return type;
	}

	@Override
	public long getSize() {
		return sz;
	}

	@Override
	public ObjectStream openStream() throws MissingObjectException
		PackChunk pc = firstChunk;
		if (pc != null)
			firstChunk = null;
		else
			pc = ctx.getChunk(meta.getFragmentKey(0));

		InputStream in = new ChunkInputStream(meta
		in = new BufferedInputStream(new InflaterInputStream(in)
		return new ObjectStream.Filter(type
	}

	private static class ChunkInputStream extends InputStream {
		private final ChunkMeta meta;

		private final DhtReader ctx;

		private int ptr;

		private PackChunk pc;

		private int fragment;

		ChunkInputStream(ChunkMeta meta
			this.ctx = ctx;
			this.meta = meta;
			this.ptr = pos;
			this.pc = pc;
		}

		@Override
		public int read(byte[] dstbuf
				throws IOException {
			if (0 == dstlen)
				return 0;

			int n = pc.read(ptr
			if (n == 0) {
				if (fragment == meta.getFragmentCount())
					return -1;

				pc = ctx.getChunk(meta.getFragmentKey(++fragment));
				ptr = 0;
				n = pc.read(ptr
				if (n == 0)
					return -1;
			}
			ptr += n;
			return n;
		}

		@Override
		public int read() throws IOException {
			byte[] tmp = new byte[1];
			int n = read(tmp
			return n == 1 ? tmp[0] & 0xff : -1;
		}
	}
}
