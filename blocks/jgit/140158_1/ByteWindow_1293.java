
package org.eclipse.jgit.internal.storage.file;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import org.eclipse.jgit.internal.storage.pack.PackOutputStream;

final class ByteBufferWindow extends ByteWindow {
	private final ByteBuffer buffer;

	ByteBufferWindow(PackFile pack
		super(pack
		buffer = b;
	}

	@Override
	protected int copy(int p
		final ByteBuffer s = buffer.slice();
		s.position(p);
		n = Math.min(s.remaining()
		s.get(b
		return n;
	}

	@Override
	void write(PackOutputStream out
			throws IOException {
		final ByteBuffer s = buffer.slice();
		s.position((int) (pos - start));

		while (0 < cnt) {
			byte[] buf = out.getCopyBuffer();
			int n = Math.min(cnt
			s.get(buf
			out.write(buf
			cnt -= n;
		}
	}

	@Override
	protected int setInput(int pos
			throws DataFormatException {
		final ByteBuffer s = buffer.slice();
		s.position(pos);
		final byte[] tmp = new byte[Math.min(s.remaining()
		s.get(tmp
		inf.setInput(tmp
		return tmp.length;
	}
}
