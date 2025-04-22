
package org.eclipse.jgit.internal.storage.reftable;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.internal.storage.io.BlockSource;

class MemoryReftable {
	final int blockSize;
	final List<byte[]> blocks;
	long size;
	byte[] buf;
	int bufPtr;

	MemoryReftable(int blockSize) {
		this.blockSize = blockSize;

		buf = new byte[blockSize];
		blocks = new ArrayList<>();
		blocks.add(buf);
	}

	void checkBuffer() {
		if (bufPtr == blockSize) {
			bufPtr = 0;
			buf = new byte[blockSize];
			blocks.add(buf);
		}
	}

	OutputStream getOutput() {
		return new OutputStream() {
			@Override
			public void write(int b) {
				checkBuffer();
				buf[bufPtr++] = (byte) b;
				size++;
			}

			@Override
			public void write(byte[] src
				while (n > 0) {
					checkBuffer();
					int c = Math.min(n
					System.arraycopy(src
					bufPtr += c;
					size += c;
					n -= c;
					p += c;
				}
			}
		};
	}

	BlockSource getBlockSource() {
		return new BlockSource() {
			@Override
			public ByteBuffer read(long pos
				if (pos >= size) {
					return ByteBuffer.allocate(0);
				}

				int idx = (int) (pos / blockSize);
				int ptr = (int) (pos - (idx * blockSize));
				byte[] src = blocks.get(idx);
				int len = src == buf ? bufPtr : blockSize;
				if (ptr == 0 && sz <= len) {
					ByteBuffer b = ByteBuffer.wrap(src);
					b.position(Math.min(sz
					return b;
				}

				ByteBuffer b = ByteBuffer.allocate(sz);
				while (pos < size && b.hasRemaining()) {
					idx = (int) (pos / blockSize);
					ptr = (int) (pos - (idx * blockSize));
					src = blocks.get(idx);
					len = src == buf ? bufPtr : blockSize;
					int n = Math.min(b.remaining()
					b.put(src
					pos += n;
				}
				return b;
			}

			@Override
			public long size() throws IOException {
				return size;
			}

			@Override
			public void close() {
			}
		};
	}
}
