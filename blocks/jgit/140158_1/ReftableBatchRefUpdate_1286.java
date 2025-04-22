
package org.eclipse.jgit.internal.storage.dfs;

import java.io.IOException;
import java.nio.channels.ReadableByteChannel;

public interface ReadableChannel extends ReadableByteChannel {
	long position() throws IOException;

	void position(long newPosition) throws IOException;

	long size() throws IOException;

	int blockSize();

	void setReadAheadBytes(int bufferSize) throws IOException;
}
