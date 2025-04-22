
package org.eclipse.jgit.junit;

import static org.junit.Assert.assertEquals;

import org.eclipse.jgit.lib.ProgressMonitor;

public final class StrictWorkMonitor implements ProgressMonitor {
	private int lastWork

	@Override
	public void start(int totalTasks) {
	}

	@Override
	public void beginTask(String title
		this.totalWork = total;
		lastWork = 0;
	}

	@Override
	public void update(int completed) {
		lastWork += completed;
	}

	@Override
	public void endTask() {
		assertEquals("Units of work recorded"
	}

	@Override
	public boolean isCancelled() {
		return false;
	}
}
