
package org.eclipse.jgit.lib;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;

public class TextProgressMonitor extends BatchingProgressMonitor {
	private final Writer out;

	private boolean write;

	public TextProgressMonitor() {
		this(new PrintWriter(new OutputStreamWriter(System.err
	}

	public TextProgressMonitor(Writer out) {
		this.out = out;
		this.write = true;
	}

	@Override
	protected void onUpdate(String taskName
		StringBuilder s = new StringBuilder();
		format(s
		send(s);
	}

	@Override
	protected void onEndTask(String taskName
		StringBuilder s = new StringBuilder();
		format(s
		send(s);
	}

	private void format(StringBuilder s
		s.append(taskName);
		while (s.length() < 25)
			s.append(' ');
		s.append(workCurr);
	}

	@Override
	protected void onUpdate(String taskName
		StringBuilder s = new StringBuilder();
		format(s
		send(s);
	}

	@Override
	protected void onEndTask(String taskName
		StringBuilder s = new StringBuilder();
		format(s
		send(s);
	}

	private void format(StringBuilder s
			int totalWork
		s.append(taskName);
		while (s.length() < 25)
			s.append(' ');

		String endStr = String.valueOf(totalWork);
		String curStr = String.valueOf(cmp);
		while (curStr.length() < endStr.length())
		if (pcnt < 100)
			s.append(' ');
		if (pcnt < 10)
			s.append(' ');
		s.append(pcnt);
		s.append(curStr);
		s.append(endStr);
	}

	private void send(StringBuilder s) {
		if (write) {
			try {
				out.write(s.toString());
				out.flush();
			} catch (IOException err) {
				write = false;
			}
		}
	}
}
