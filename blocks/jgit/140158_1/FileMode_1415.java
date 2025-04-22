package org.eclipse.jgit.lib;

public abstract class EmptyProgressMonitor implements ProgressMonitor {

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
	public void endTask() {
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

}
