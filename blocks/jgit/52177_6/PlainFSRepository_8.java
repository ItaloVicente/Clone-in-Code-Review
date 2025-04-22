package org.eclipse.jgit.lfs.lib;

import java.io.IOException;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public interface LargeFileRepository {

	public boolean exists(AnyLongObjectId id);

	public long getLength(AnyLongObjectId id) throws IOException;

	public ReadableByteChannel getReadChannel(AnyLongObjectId id)
			throws IOException;

	public WritableByteChannel getWriteChannel(AnyLongObjectId id)
			throws IOException;

	public void abortWrite();
}
