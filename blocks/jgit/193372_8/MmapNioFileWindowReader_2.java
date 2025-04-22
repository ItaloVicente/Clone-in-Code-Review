package org.eclipse.jgit.internal.storage.file;

import java.io.IOException;
import java.io.RandomAccessFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeapFileWindowReader implements FileWindowReader {
	private static final Logger LOG = LoggerFactory
			.getLogger(HeapFileWindowReader.class);
	private final Pack pack;
	private RandomAccessFile fd;

	private long length;

	HeapFileWindowReader(Pack pack) {
		this.pack = pack;
	}

	@Override
	public FileWindowReader open() throws IOException {
		fd = new RandomAccessFile(pack.getPackFile()
		length = fd.length();
		return this;
	}

	@Override
	public void close() throws Exception {
		if (fd != null) {
			try {
				fd.close();
			} catch (IOException e) {
				LOG.error(e.getMessage()
			}
			fd = null;
		}
	}

	@Override
	public long length() {
		return length;
	}

	@Override
	public ByteWindow read(long pos
		if (length < pos + size) {
			size = (int) (length - pos);
		}
		final byte[] buf = new byte[size];
		fd.seek(pos);
		fd.readFully(buf
		return new ByteArrayWindow(pack
	}

	@Override
	public void readRaw(byte[] b
		fd.seek(off);
		fd.readFully(b
	}

}
