package org.eclipse.jgit.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class Command {
	protected InputStream in;

	protected OutputStream out;

	public int run() throws IOException {
		for (int b = in.read(); b != -1; b = in.read())
			process(b);
		return 0;
	}

	protected abstract void process(int b) throws IOException;
}
