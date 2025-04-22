
package org.eclipse.jgit.internal.storage.dfs;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.eclipse.jgit.internal.storage.pack.PackOutputStream;

final class DfsBlock {
	final DfsStreamKey stream;
	final long start;
	final long end;
	private final byte[] block;

	DfsBlock(DfsStreamKey p
		stream = p;
		start = pos;
		end = pos + buf.length;
		block = buf;
	}

	int size() {
		return block.length;
	}

	ByteBuffer zeroCopyByteBuffer(int n) {
		ByteBuffer b = ByteBuffer.wrap(block);
		b.position(n);
		return b;
	}

	boolean contains(DfsStreamKey want
		return stream.equals(want) && start <= pos && pos < end;
	}

	int copy(long pos
		int ptr = (int) (pos - start);
		return copy(ptr
	}

	int copy(int p
		n = Math.min(block.length - p
		System.arraycopy(block
		return n;
	}

	int setInput(long pos
		int ptr = (int) (pos - start);
		int cnt = block.length - ptr;
		if (cnt <= 0) {
		}
		inf.setInput(block
		return cnt;
	}

	void crc32(CRC32 out
		int ptr = (int) (pos - start);
		out.update(block
	}

	void write(PackOutputStream out
			throws IOException {
		out.write(block
	}

	void check(Inflater inf
			throws DataFormatException {
		inf.setInput(block
		while (inf.inflate(tmp
			continue;
	}
}
