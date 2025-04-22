
package org.eclipse.jgit.internal.storage.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public abstract class BlockSource implements AutoCloseable {
	public static BlockSource from(byte[] content) {
		return new BlockSource() {
			@Override
			public ByteBuffer read(long pos
				ByteBuffer buf = ByteBuffer.allocate(cnt);
				if (pos < content.length) {
					int p = (int) pos;
					int n = Math.min(cnt
					buf.put(content
				}
				return buf;
			}

			@Override
			public long size() {
				return content.length;
			}

			@Override
			public void close() {
			}
		};
	}

	public static BlockSource from(FileInputStream in) {
		return from(in.getChannel());
	}

	public static BlockSource from(FileChannel ch) {
		return new BlockSource() {
			@Override
			public ByteBuffer read(long pos
				ByteBuffer b = ByteBuffer.allocate(blockSize);
				ch.position(pos);
				int n;
				do {
					n = ch.read(b);
				} while (n > 0 && b.position() < blockSize);
				return b;
			}

			@Override
			public long size() throws IOException {
				return ch.size();
			}

			@Override
			public void close() {
				try {
					ch.close();
				} catch (IOException e) {
				}
			}
		};
	}

	public abstract ByteBuffer read(long position
			throws IOException;

	public abstract long size() throws IOException;

	public void adviseSequentialRead(long startPos
	}

	@Override
	public abstract void close();
}
