
package org.eclipse.jgit.storage.file;

import java.io.IOException;
import java.io.InputStream;

class PackInputStream extends InputStream {
	private final WindowCursor wc;

	private final PackFile pack;

	private long pos;

	PackInputStream(PackFile pack
			throws IOException {
		this.pack = pack;
		this.pos = pos;
		this.wc = wc;

		wc.pin(pack
	}

	@Override
	public int read(byte[] b
		int n = wc.copy(pack
		pos += n;
		return n;
	}

	@Override
	public int read() throws IOException {
		byte[] buf = new byte[1];
		int n = read(buf
		return n == 1 ? buf[0] & 0xff : -1;
	}

	@Override
	public void close() {
		wc.release();
	}
}
