
package org.eclipse.jgit.storage.dfs;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public abstract class DfsOutputStream extends OutputStream {
	public int blockSize() {
		return 0;
	}

	@Override
	public void write(int b) throws IOException {
		write(new byte[] { (byte) b });
	}

	@Override
	public abstract void write(byte[] buf

	public abstract int read(long position
}
