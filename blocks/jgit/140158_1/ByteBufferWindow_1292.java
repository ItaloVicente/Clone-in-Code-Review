
package org.eclipse.jgit.internal.storage.file;

import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.eclipse.jgit.internal.storage.pack.PackOutputStream;

final class ByteArrayWindow extends ByteWindow {
	private final byte[] array;

	ByteArrayWindow(PackFile pack
		super(pack
		array = b;
	}

	@Override
	protected int copy(int p
		n = Math.min(array.length - p
		System.arraycopy(array
		return n;
	}

	@Override
	protected int setInput(int pos
			throws DataFormatException {
		int n = array.length - pos;
		inf.setInput(array
		return n;
	}

	void crc32(CRC32 out
		out.update(array
	}

	@Override
	void write(PackOutputStream out
			throws IOException {
		int ptr = (int) (pos - start);
		out.write(array
	}

	void check(Inflater inf
			throws DataFormatException {
		inf.setInput(array
		while (inf.inflate(tmp
			continue;
	}
}
