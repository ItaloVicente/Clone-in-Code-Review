package org.eclipse.core.tests.databinding.observable;

import java.util.LinkedList;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.runtime.Assert;

public class ThreadRealm extends Realm {
    private Thread thread;

    private final LinkedList<Runnable> queue = new LinkedList<Runnable>();

    private volatile boolean block;

    public synchronized void init(Thread thread) {
        if (thread == null) {
            throw new IllegalArgumentException("Parameter thread was null."); //$NON-NLS-1$
        }
        Assert.isTrue(this.thread == null, "Realm can only be initialized once.");

        this.thread = thread;
    }

    @Override
	public boolean isCurrent() {
        return Thread.currentThread() == thread;
    }

    public Thread getThread() {
        return thread;
    }

    @Override
	public void asyncExec(Runnable runnable) {
        synchronized (queue) {
            queue.add(runnable);
            queue.notifyAll();
        }
    }

    public void processQueue() {
        if (Thread.currentThread() == thread) {
            throw new IllegalStateException(
                    "Cannot execute this method in the realm's own thread");
        }

        try {
            synchronized (queue) {
                while (!queue.isEmpty()) {
                    if (!block)
                        throw new IllegalStateException(
                                "Cannot process queue, ThreadRealm is not blocking on its thread");
                    queue.wait();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean isBlocking() {
        return block;
    }

    public void block() {
        if (block) {
            throw new IllegalStateException("Realm is already blocking.");
        }

        if (Thread.currentThread() != thread) {
            throw new IllegalStateException("The current thread is not the correct thread.");
        }

        try {
            block = true;

            synchronized (queue) {
                queue.notifyAll(); // so waitUntilBlocking can return
            }

            while (block) {
                Runnable runnable = null;
                synchronized (queue) {
                    if (queue.isEmpty()) {
                        queue.wait();
                    } else {
                        runnable = queue.getFirst();
                    }
                }

                if (runnable != null) {
                    safeRun(runnable);
                    synchronized (queue) {
                        queue.removeFirst();
                        queue.notifyAll();
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            block = false;
        }
    }

    public void unblock() {
        block = false;

        synchronized (queue) {
            queue.notifyAll();
        }
    }

    public void waitUntilBlocking() {
        if (Thread.currentThread() == thread) {
            throw new IllegalStateException(
                    "Cannot execute this method in the realm's own thread");
        }

        while (!block) {
            synchronized (queue) {
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
