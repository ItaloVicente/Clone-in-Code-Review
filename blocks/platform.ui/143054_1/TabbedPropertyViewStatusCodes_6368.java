package org.eclipse.ui.internal.views.log;

import java.io.*;

public class TailInputStream extends InputStream {

	private RandomAccessFile fRaf;

	private long fTail;

	public TailInputStream(File file, long maxLength) throws IOException {
		super();
		fTail = maxLength;
		fRaf = new RandomAccessFile(file, "r"); //$NON-NLS-1$
		skipHead(file);
	}

	private void skipHead(File file) throws IOException {
		if (file.length() > fTail) {
			fRaf.seek(file.length() - fTail);
			int c = read();
			while (c != '\n' && c != '\r' && c != -1) {
				c = read();
			}

		}
	}

	@Override
	public int read() throws IOException {
		byte[] b = new byte[1];
		int len = fRaf.read(b, 0, 1);
		if (len < 0) {
			return len;
		}
		return b[0];
	}

	@Override
	public int read(byte[] b) throws IOException {
		return fRaf.read(b, 0, b.length);
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		return fRaf.read(b, off, len);
	}

	@Override
	public void close() throws IOException {
		fRaf.close();
	}

}
