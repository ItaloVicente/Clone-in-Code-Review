
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.jgit.lib.BatchingProgressMonitor;
import org.eclipse.jgit.lib.Constants;

class SideBandProgressMonitor extends BatchingProgressMonitor {
	private final OutputStream out;

	private boolean write;

	SideBandProgressMonitor(OutputStream os) {
		out = os;
		write = true;
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
		s.append("
		send(s);
	}

	private void format(StringBuilder s
		s.append(taskName);
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
		if (pcnt < 100)
			s.append(' ');
		if (pcnt < 10)
			s.append(' ');
		s.append(pcnt);
		s.append(cmp);
		s.append(totalWork);
	}

	private void send(StringBuilder s) {
		if (write) {
			try {
				out.write(Constants.encode(s.toString()));
				out.flush();
			} catch (IOException err) {
				write = false;
			}
		}
	}
}
