
package org.eclipse.jgit.lib;

import java.util.concurrent.locks.ReentrantLock;

public class ThreadSafeProgressMonitor implements ProgressMonitor {
	private final ProgressMonitor pm;

	private final ReentrantLock lock;

	public ThreadSafeProgressMonitor(ProgressMonitor pm) {
		this.pm = pm;
		this.lock = new ReentrantLock();
	}

	public void start(int totalTasks) {
		lock.lock();
		try {
			pm.start(totalTasks);
		} finally {
			lock.unlock();
		}
	}

	public void beginTask(String title
		lock.lock();
		try {
			pm.beginTask(title
		} finally {
			lock.unlock();
		}
	}

	public void update(int completed) {
		lock.lock();
		try {
			pm.update(completed);
		} finally {
			lock.unlock();
		}
	}

	public boolean isCancelled() {
		lock.lock();
		try {
			return pm.isCancelled();
		} finally {
			lock.unlock();
		}
	}

	public void endTask() {
		lock.lock();
		try {
			pm.endTask();
		} finally {
			lock.unlock();
		}
	}
}
