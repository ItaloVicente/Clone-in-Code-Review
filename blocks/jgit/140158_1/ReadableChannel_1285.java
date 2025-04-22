
package org.eclipse.jgit.internal.storage.dfs;

import java.io.IOException;
import java.io.InputStream;

final class PackInputStream extends InputStream {
	final DfsReader ctx;

	private final DfsPackFile pack;

	private long pos;

	PackInputStream(DfsPackFile pack
			throws IOException {
		this.pack = pack;
		this.pos = pos;
		this.ctx = ctx;

		ctx.pin(pack
	}

	@Override
	public int read(byte[] b
		int n = ctx.copy(pack
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
		ctx.close();
	}
}
