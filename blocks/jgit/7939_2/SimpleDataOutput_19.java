
package org.eclipse.jgit.storage.file;

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

	public void writeInt(int v) throws IOException {
		NB.encodeInt32(buf
		fd.write(buf
	}

	public void writeLong(long v) throws IOException {
		NB.encodeInt64(buf
		fd.write(buf
	}

	public void write(int b) throws IOException {
		throw new UnsupportedOperationException();
	}

	public void write(byte[] b) throws IOException {
		throw new UnsupportedOperationException();
	}

	public void write(byte[] b
		throw new UnsupportedOperationException();
	}

	public void writeBoolean(boolean v) throws IOException {
		throw new UnsupportedOperationException();
	}

	public void writeByte(int v) throws IOException {
		throw new UnsupportedOperationException();
	}

	public void writeShort(int v) throws IOException {
		throw new UnsupportedOperationException();
	}

	public void writeChar(int v) throws IOException {
		throw new UnsupportedOperationException();
	}

	public void writeFloat(float v) throws IOException {
		throw new UnsupportedOperationException();
	}

	public void writeDouble(double v) throws IOException {
		throw new UnsupportedOperationException();
	}

	public void writeBytes(String s) throws IOException {
		throw new UnsupportedOperationException();
	}

	public void writeChars(String s) throws IOException {
		throw new UnsupportedOperationException();
	}

	public void writeUTF(String s) throws IOException {
		throw new UnsupportedOperationException();
	}
}
