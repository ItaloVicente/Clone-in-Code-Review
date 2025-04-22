package org.eclipse.jgit.internal.storage.file;

import java.io.EOFException;
import java.io.IOException;

public interface FileWindowReader extends AutoCloseable {

	FileWindowReader open() throws IOException;

	long length() throws IOException;

	ByteWindow read(long pos

	void readRaw(byte b[]
}
