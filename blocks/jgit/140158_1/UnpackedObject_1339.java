
package org.eclipse.jgit.internal.storage.file;

import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.jgit.util.NB;

class SimpleDataOutput implements DataOutput {
	private final OutputStream fd;

	private final byte[] buf = new byte[8];

	SimpleDataOutput(OutputStream fd) {
		this.fd = fd;
	}

	@Override
	public void writeShort(int v) throws IOException {
		NB.encodeInt16(buf
		fd.write(buf
	}

	@Override
	public void writeInt(int v) throws IOException {
		NB.encodeInt32(buf
		fd.write(buf
	}

	@Override
	public void writeLong(long v) throws IOException {
		NB.encodeInt64(buf
		fd.write(buf
	}

	@Override
	public void write(int b) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void write(byte[] b) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void write(byte[] b
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeBoolean(boolean v) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeByte(int v) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeChar(int v) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeFloat(float v) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeDouble(double v) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeBytes(String s) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeChars(String s) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void writeUTF(String s) throws IOException {
		throw new UnsupportedOperationException();
	}
}
