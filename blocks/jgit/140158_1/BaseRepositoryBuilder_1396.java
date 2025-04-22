
package org.eclipse.jgit.lib;

public interface AsyncOperation {
	boolean cancel(boolean mayInterruptIfRunning);

	void release();
}
