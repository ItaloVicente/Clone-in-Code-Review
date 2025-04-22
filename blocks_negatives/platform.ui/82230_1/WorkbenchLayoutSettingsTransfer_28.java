/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.internal;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public final class PendingSyncExec {
	private final Semaphore semaphore = new Semaphore(0);

	private Thread operation;

	private final Runnable runnable;

	private boolean hasFinishedRunning;

    public PendingSyncExec(Runnable runnable) {
        this.runnable = runnable;
    }

    /**
     * Attempts to acquire this semaphore.  Returns true if it was successfully acquired,
     * and false otherwise.
     */
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
		while (!acquire(1000)) {
			if (lockListener.isUIWaiting()) {
				synchronized (this) {
					if (!hasFinishedRunning) {
						lockListener.interruptUI(runnable);
					}
				}
			}
		}
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
    }
}
