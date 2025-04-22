
package org.eclipse.jgit.internal.storage.file;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jgit.util.NB;

class XInputStream extends BufferedInputStream {
	private final byte[] intbuf = new byte[8];

	XInputStream(InputStream s) {
		super(s);
	}

	synchronized byte[] readFully(final int len) throws IOException {
		final byte[] b = new byte[len];
		readFully(b
		return b;
	}

	synchronized void readFully(byte[] b
			throws IOException {
		int r;
		while (len > 0 && (r = read(b
			o += r;
			len -= r;
		}
		if (len > 0)
			throw new EOFException();
	}

	int readUInt8() throws IOException {
		final int r = read();
		if (r < 0)
			throw new EOFException();
		return r;
	}

	long readUInt32() throws IOException {
		readFully(intbuf
		return NB.decodeUInt32(intbuf
	}
}
