
package org.eclipse.jgit.lib;

public interface ProgressMonitor {
	int UNKNOWN = 0;

	void start(int totalTasks);

	void beginTask(String title

	void update(int completed);

	void endTask();

	boolean isCancelled();
}
