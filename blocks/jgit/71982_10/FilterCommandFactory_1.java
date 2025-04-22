package org.eclipse.jgit.attributes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class FilterCommand {
	protected InputStream in;

	protected OutputStream out;

	public FilterCommand(InputStream in
		this.in = in;
		this.out = out;
	}

	public abstract int run() throws IOException;
}
