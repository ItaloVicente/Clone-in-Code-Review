package org.eclipse.jgit.util.io;

import java.io.OutputStream;

public class NullOutputStream extends OutputStream {

	public static final NullOutputStream INSTANCE = new NullOutputStream();

	private NullOutputStream() {
	}

	@Override
	public void write(int b) {
	}

	@Override
	public void write(byte[] buf) {
	}

	@Override
	public void write(byte[] buf
	}
}
