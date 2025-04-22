package org.eclipse.ui.internal;

import org.eclipse.core.runtime.jobs.LockListener;
import org.eclipse.swt.widgets.Display;

public class UILockListener extends LockListener {

    public class Queue {
        private static final int BASE_SIZE = 8;

        protected Semaphore[] elements = new Semaphore[BASE_SIZE];

        protected int head = 0;

        protected int tail = 0;

        public synchronized void add(Semaphore element) {
            int newTail = increment(tail);
            if (newTail == head) {
                grow();
                newTail = tail + 1;
            }
            elements[tail] = element;
            tail = newTail;
        }

        private void grow() {
            int newSize = elements.length * 2;
            Semaphore[] newElements = new Semaphore[newSize];
            if (tail >= head) {
				System.arraycopy(elements, head, newElements, head, size());
			} else {
                int newHead = newSize - (elements.length - head);
                System.arraycopy(elements, 0, newElements, 0, tail + 1);
                System.arraycopy(elements, head, newElements, newHead,
                        (newSize - newHead));
                head = newHead;
            }
            elements = newElements;
        }

        private int increment(int index) {
            return (index == (elements.length - 1)) ? 0 : index + 1;
        }

        public synchronized Semaphore remove() {
            if (tail == head) {
				return null;
			}
            Semaphore result = elements[head];
            elements[head] = null;
            head = increment(head);
            if (tail == head && elements.length > BASE_SIZE) {
                elements = new Semaphore[BASE_SIZE];
                tail = head = 0;
            }
            return result;
        }

        private int size() {
            return tail > head ? (tail - head)
                    : ((elements.length - head) + tail);
        }
    }

    protected Display display;

    protected final Queue pendingWork = new Queue();

    protected Semaphore currentWork = null;

    protected Thread ui;

    public UILockListener(Display display) {
        this.display = display;
    }

    @Override
	public void aboutToRelease() {
        if (isUI()) {
			ui = null;
		}
    }

    @Override
	public boolean aboutToWait(Thread lockOwner) {
        if (isUI()) {
            if (currentWork != null
                    && currentWork.getOperationThread() == lockOwner) {
				return true;
			}
            ui = Thread.currentThread();
            try {
                doPendingWork();
            } finally {
                ui = Thread.currentThread();
            }
        }
        return false;
    }

    void addPendingWork(Semaphore work) {
        pendingWork.add(work);
    }

	@Override
	public boolean canBlock() {
		return !isUI();
	}

    void doPendingWork() {
    	Thread.interrupted();
        Semaphore work;
        while ((work = pendingWork.remove()) != null) {
        	Semaphore oldWork = currentWork;
            try {
                currentWork = work;
                Runnable runnable = work.getRunnable();
                if (runnable != null) {
					runnable.run();
				}

            } finally {
                currentWork = oldWork;
                work.release();
            }
        }
    }

    void interruptUI() {
        display.getThread().interrupt();
    }

    boolean isLockOwner() {
        return isLockOwnerThread();
    }

    boolean isUI() {
        return (!display.isDisposed())
                && (display.getThread() == Thread.currentThread());
    }

    boolean isUIWaiting() {
        return (ui != null) && (Thread.currentThread() != ui);
    }
}
