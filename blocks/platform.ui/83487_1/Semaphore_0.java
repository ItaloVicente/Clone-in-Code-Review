package org.eclipse.ui.internal;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public final class PendingSyncExec {
	private Semaphore semaphore = new Semaphore(0);

	private Thread operation;

	private Runnable runnable;

	private boolean hasFinishedRunning = false;

    public PendingSyncExec(Runnable runnable) {
        this.runnable = runnable;
    }

	private boolean acquire(long delay) throws InterruptedException {
		return semaphore.tryAcquire(delay, TimeUnit.MILLISECONDS);
    }

    @Override
	public boolean equals(Object obj) {
        return (runnable == ((PendingSyncExec) obj).runnable);
    }

    public Thread getOperationThread() {
        return operation;
    }

	public void run() {
		Thread.interrupted();
		try {
			if (runnable != null) {
				runnable.run();
			}
		} finally {
			synchronized (this) {
				hasFinishedRunning = true;
			}
			Thread.interrupted();
			semaphore.release();
		}
	}

	public void waitUntilExecuted(UILockListener lockListener) throws InterruptedException {
		do {
			if (lockListener.isUIWaiting()) {
				synchronized (this) {
					if (!hasFinishedRunning) {
						lockListener.interruptUI();
					}
				}
			}
		} while (!acquire(1000));
	}

    @Override
	public int hashCode() {
        return runnable == null ? 0 : runnable.hashCode();
    }

    public void setOperationThread(Thread operation) {
        this.operation = operation;
    }

    @Override
	public String toString() {
		return "PendingSyncExec(" + runnable + ")"; //$NON-NLS-1$ //$NON-NLS-2$
    }
}
