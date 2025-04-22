
package org.eclipse.jgit.storage.dfs;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.eclipse.jgit.storage.pack.PackOutputStream;

final class DfsBlock {
	private static final int INFLATE_STRIDE = 512;

	final DfsPackKey pack;

	final long start;

	final long end;

	private final byte[] block;

	DfsBlock(DfsPackKey p
		pack = p;
		start = pos;
		end = pos + buf.length;
		block = buf;
	}

	int size() {
		return block.length;
	}

	int remaining(long pos) {
		int ptr = (int) (pos - start);
		return block.length - ptr;
	}

	boolean contains(DfsPackKey want
		return pack == want && start <= pos && pos < end;
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

	int inflate(Inflater inf
			throws DataFormatException {
		int ptr = (int) (pos - start);
		int in = Math.min(INFLATE_STRIDE
		in = Math.min(in
		inf.setInput(block

		for (;;) {
			int out = inf.inflate(dstbuf
			if (out == 0) {
				if (inf.needsInput()) {
					ptr += in;
					in = Math.min(INFLATE_STRIDE
					if (in == 0)
						return dstoff;
					inf.setInput(block
					continue;
				} else if (inf.finished())
					return dstoff;
				else
					throw new DataFormatException();
			}
			dstoff += out;
		}
	}

	void crc32(CRC32 out
		int ptr = (int) (pos - start);
		out.update(block
	}

	void write(PackOutputStream out
			throws IOException {
		int ptr = (int) (pos - start);
		out.write(block
		if (digest != null)
			digest.update(block
	}

	void check(Inflater inf
			throws DataFormatException {
		inf.setInput(block
		while (inf.inflate(tmp
			continue;
	}
}
