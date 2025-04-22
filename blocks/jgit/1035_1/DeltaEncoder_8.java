
package org.eclipse.jgit.storage.pack;

import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.jgit.lib.Constants;

public class DeltaEncoder {
	private static final int MAX_COPY = (0xff << 16) + (0xff << 8) + 0xff;

	private final OutputStream out;

	private final byte[] buf = new byte[16];

	private int size;

	public DeltaEncoder(OutputStream out
			throws IOException {
		this.out = out;
		writeVarint(baseSize);
		writeVarint(resultSize);
	}

	private void writeVarint(long sz) throws IOException {
		int p = 0;
		while (sz > 0x80) {
			buf[p++] = (byte) (0x80 | (((int) sz) & 0x7f));
			sz >>>= 7;
		}
		buf[p++] = (byte) (((int) sz) & 0x7f);
		out.write(buf
		size += p;
	}

	public int getSize() {
		return size;
	}

	public void insert(String text) throws IOException {
		insert(Constants.encode(text));
	}

	public void insert(byte[] text) throws IOException {
		insert(text
	}

	public void insert(byte[] text
		while (0 < cnt) {
			int n = Math.min(127
			out.write((byte) n);
			out.write(text
			off += n;
			cnt -= n;
			size += 1 + n;
		}
	}

	public void copy(long offset
		if (cnt > MAX_COPY) {
			copy(offset
			offset += MAX_COPY;
			cnt -= MAX_COPY;
		}

		int cmd = 0x80;
		int p = 1;

		if ((offset & 0xff) != 0) {
			cmd |= 0x01;
			buf[p++] = (byte) (offset & 0xff);
		}
		if ((offset & (0xff << 8)) != 0) {
			cmd |= 0x02;
			buf[p++] = (byte) ((offset >>> 8) & 0xff);
		}
		if ((offset & (0xff << 16)) != 0) {
			cmd |= 0x04;
			buf[p++] = (byte) ((offset >>> 16) & 0xff);
		}
		if ((offset & (0xff << 24)) != 0) {
			cmd |= 0x08;
			buf[p++] = (byte) ((offset >>> 24) & 0xff);
		}

		if (cnt != 0x10000) {
			if ((cnt & 0xff) != 0) {
				cmd |= 0x10;
				buf[p++] = (byte) (cnt & 0xff);
			}
			if ((cnt & (0xff << 8)) != 0) {
				cmd |= 0x20;
				buf[p++] = (byte) ((cnt >>> 8) & 0xff);
			}
			if ((cnt & (0xff << 16)) != 0) {
				cmd |= 0x40;
				buf[p++] = (byte) ((cnt >>> 16) & 0xff);
			}
		}

		buf[0] = (byte) cmd;
		out.write(buf
		size += p;
	}
}
