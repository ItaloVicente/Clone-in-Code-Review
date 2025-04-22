
package org.eclipse.core.tests.databinding.observable;

import java.util.LinkedList;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ILock;
import org.eclipse.core.runtime.jobs.Job;

public class LockRealm extends Realm {

	private LinkedList<Runnable> queue;
	private ILock lock;
	private Job job;
	private boolean lockAcquired;

	public LockRealm() {
		queue = new LinkedList<Runnable>();
		lock = Job.getJobManager().newLock();
		job = new Job("Lock Realm Job") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				for (Runnable runnable; (runnable = dequeue()) != null;) {
					acquireLock();
					try {
						safeRun(runnable);
					} finally {
						releaseLock();
					}
				}
				return Status.OK_STATUS;
			}
		};
		job.setSystem(true);
	}

	@Override
	protected void syncExec(Runnable runnable) {
		acquireLock();
		try {
			safeRun(runnable);
		} finally {
			releaseLock();
		}
	}

	@Override
	public void asyncExec(Runnable runnable) {
		enqueue(runnable);
		job.schedule();
	}

	private void enqueue(Runnable runnable) {
		synchronized (queue) {
			queue.addLast(runnable);
		}
	}

	private Runnable dequeue() {
		synchronized (queue) {
			if (queue.isEmpty()) {
				return null;
			}
			return queue.getFirst();
		}
	}

	@Override
	public boolean isCurrent() {
		return lockAcquired;
	}

	private void acquireLock() {
		lock.acquire();
		lockAcquired = true;
	}

	private void releaseLock() {
		lockAcquired = false;
		lock.release();
	}

}
