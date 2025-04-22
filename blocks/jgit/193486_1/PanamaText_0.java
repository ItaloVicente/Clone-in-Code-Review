package org.eclipse.jgit.panama.internal;

import java.io.IOException;

import org.eclipse.jgit.internal.storage.file.ByteWindow;
import org.eclipse.jgit.internal.storage.file.FileWindowReader;

public class PanamaFileWindowReader implements FileWindowReader {

	@Override
	public void close() throws Exception {

	}

	@Override
	public FileWindowReader open() throws IOException {
		return null;
	}

	@Override
	public long length() throws IOException {
		return 0;
	}

	@Override
	public ByteWindow read(long pos
		return null;
	}

	@Override
	public void readRaw(byte[] b

	}

}
