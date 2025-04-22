
package org.eclipse.jgit.internal.storage.file;

import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.NB;

class SimpleDataInput implements DataInput {
	private final InputStream fd;

	private final byte[] buf = new byte[8];

	SimpleDataInput(InputStream fd) {
		this.fd = fd;
	}

	@Override
	public int readInt() throws IOException {
		readFully(buf
		return NB.decodeInt32(buf
	}

	@Override
	public long readLong() throws IOException {
		readFully(buf
		return NB.decodeInt64(buf
	}

	public long readUnsignedInt() throws IOException {
		readFully(buf
		return NB.decodeUInt32(buf
	}

	@Override
	public void readFully(byte[] b) throws IOException {
		readFully(b
	}

	@Override
	public void readFully(byte[] b
		IO.readFully(fd
	}

	@Override
	public int skipBytes(int n) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean readBoolean() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public byte readByte() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int readUnsignedByte() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public short readShort() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int readUnsignedShort() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public char readChar() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public float readFloat() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public double readDouble() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String readLine() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String readUTF() throws IOException {
		throw new UnsupportedOperationException();
	}
}
