package org.eclipse.ui.internal;

public class Semaphore {
    protected long notifications;

    protected Thread operation;

    protected Runnable runnable;

    public Semaphore(Runnable runnable) {
        this.runnable = runnable;
        notifications = 0;
    }

    public synchronized boolean acquire(long delay) throws InterruptedException {
        if (Thread.interrupted()) {
			throw new InterruptedException();
		}
        long start = System.currentTimeMillis();
        long timeLeft = delay;
        while (true) {
            if (notifications > 0) {
                notifications--;
                return true;
            }
            if (timeLeft <= 0) {
				return false;
			}
            wait(timeLeft);
            timeLeft = start + delay - System.currentTimeMillis();
        }
    }

    @Override
	public boolean equals(Object obj) {
        return (runnable == ((Semaphore) obj).runnable);
    }

    public Thread getOperationThread() {
        return operation;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    @Override
	public int hashCode() {
        return runnable == null ? 0 : runnable.hashCode();
    }

    public synchronized void release() {
        notifications++;
        notifyAll();
    }

    public void setOperationThread(Thread operation) {
        this.operation = operation;
    }

    @Override
	public String toString() {
        return "Semaphore(" + runnable + ")"; //$NON-NLS-1$ //$NON-NLS-2$
    }
}
