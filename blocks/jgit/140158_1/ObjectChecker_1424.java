
package org.eclipse.jgit.lib;

public class NullProgressMonitor implements ProgressMonitor {
	public static final NullProgressMonitor INSTANCE = new NullProgressMonitor();

	private NullProgressMonitor() {
	}

	@Override
	public void start(int totalTasks) {
	}

	@Override
	public void beginTask(String title
	}

	@Override
	public void update(int completed) {
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public void endTask() {
	}
}
