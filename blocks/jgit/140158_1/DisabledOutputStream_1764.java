
package org.eclipse.jgit.util.io;

import java.io.IOException;
import java.io.OutputStream;

public class CountingOutputStream extends OutputStream {
	private final OutputStream out;
	private long cnt;

	public CountingOutputStream(OutputStream out) {
		this.out = out;
	}

	public long getCount() {
		return cnt;
	}

	@Override
	public void write(int val) throws IOException {
		out.write(val);
		cnt++;
	}

	@Override
	public void write(byte[] buf
		out.write(buf
		cnt += len;
	}

	@Override
	public void flush() throws IOException {
		out.flush();
	}

	@Override
	public void close() throws IOException {
		out.close();
	}
}
