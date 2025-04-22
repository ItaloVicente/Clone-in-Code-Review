
package org.eclipse.jgit.storage.dfs;

import java.io.IOException;
import java.nio.channels.ReadableByteChannel;

public interface ReadableChannel extends ReadableByteChannel {
	public long position() throws IOException;

	public void position(long newPosition) throws IOException;

	public long size() throws IOException;

	public int blockSize();
}
