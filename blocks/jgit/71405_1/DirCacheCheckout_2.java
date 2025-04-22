package org.eclipse.jgit.commands;

import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.jgit.lib.ObjectStream;

public class SmudgeFilter extends Command
{
	public SmudgeFilter(ObjectStream in
		this.in = in;
		this.out = out;
	}

	@Override
	protected void process(int b) throws IOException {
		out.write(b);
	}

}
