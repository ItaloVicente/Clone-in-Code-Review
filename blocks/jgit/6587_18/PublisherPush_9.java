
package org.eclipse.jgit.transport;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;

public class PublisherPackSliceFile extends PublisherPackSlice {
	private final String fileName;

	public PublisherPackSliceFile(LoadPolicy policy
			byte[] buf
		super(policy
		this.fileName = fileName;
	}

	@Override
	protected byte[] doLoad() throws IOException {
		RandomAccessFile file = new RandomAccessFile(fileName
		try {
			byte[] buf = new byte[(int) file.length()];
			file.read(buf);
			return buf;
		} finally {
			file.close();
		}
	}

	@Override
	protected void doStore(byte[] buffer) throws IOException {
		FileOutputStream out = new FileOutputStream(fileName);
		try {
			out.write(buffer);
			out.flush();
		} finally {
			out.close();
		}
	}

	@Override
	protected void doStoredWrite(OutputStream out
			throws IOException {
		RandomAccessFile file = new RandomAccessFile(fileName
		try {
			byte buf[] = new byte[length];
			file.read(buf
			out.write(buf);
		} finally {
			file.close();
		}
	}

	@Override
	public void close() throws PublisherException {
		super.close();
		File f = new File(fileName);
		f.delete();
	}
}
