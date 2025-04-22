package org.eclipse.jgit.merge;

import java.io.IOException;
import java.io.OutputStream;

class EolAwareOutputStream extends OutputStream {
	private final OutputStream out;

	private boolean bol = true;

	EolAwareOutputStream(OutputStream out) {
		this.out = out;
	}

	void beginln() throws IOException {
		if (!bol)
			write('\n');
	}

	boolean isBol() {
		return bol;
	}

	@Override
	public void write(int val) throws IOException {
		out.write(val);
		bol = (val == '\n');
	}
}
