
package org.eclipse.jgit.lib;

public interface AsyncOperation {
	public boolean cancel(boolean mayInterruptIfRunning);

	public void release();
}
