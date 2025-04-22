package org.eclipse.jgit.internal.storage.file;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class MmapNioFileWindowReader implements FileWindowReader {
	private static final Logger LOG = LoggerFactory
			.getLogger(MmapNioFileWindowReader.class);
	private final Pack pack;
	private RandomAccessFile fd;
	private long length;

	MmapNioFileWindowReader(Pack pack) {
		this.pack = pack;
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
	public FileWindowReader open() throws IOException {
		fd = new RandomAccessFile(pack.getPackFile()
		length = fd.length();
		return this;
	}

	@Override
	public long length() throws IOException {
		return length;
	}

	@Override
	public ByteWindow read(long pos
		if (length < pos + size) {
			size = (int) (length - pos);
		}

		MappedByteBuffer map;
		try {
			map = fd.getChannel().map(MapMode.READ_ONLY
		} catch (IOException ioe1) {
			System.gc();
			System.runFinalization();
			map = fd.getChannel().map(MapMode.READ_ONLY
		}

		if (map.hasArray()) {
			return new ByteArrayWindow(pack
		}
		return new ByteBufferWindow(pack
	}

	@Override
	public void readRaw(byte[] b
		fd.seek(off);
		fd.readFully(b
	}

}
