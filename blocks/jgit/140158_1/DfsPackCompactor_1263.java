
package org.eclipse.jgit.internal.storage.dfs;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import org.eclipse.jgit.internal.storage.pack.PackExt;

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
