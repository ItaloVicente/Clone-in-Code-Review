package org.eclipse.jgit.internal.storage.file;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;

class MmapFileWindowReader implements FileWindowReader {
	private final Pack pack;
	private RandomAccessFile fd;
	private long length;

	MmapFileWindowReader(Pack pack) {
		this.pack = pack;
	}

	@Override
	public void close() throws Exception {
		if (fd != null) {
			RandomAccessFile toClose = fd;
			fd = null;
			toClose.close();
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
