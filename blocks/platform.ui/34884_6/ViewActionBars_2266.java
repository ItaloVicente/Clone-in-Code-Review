package org.eclipse.ui.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Synchronizer;
import org.eclipse.ui.internal.StartupThreading.StartupRunnable;

public class UISynchronizer extends Synchronizer {
    protected UILockListener lockListener;
    
	protected boolean isStarting = true;

	protected List pendingStartup = new ArrayList();

	private boolean use32Threading = false;

	public static final ThreadLocal startupThread = new ThreadLocal() {

		@Override
		protected Object initialValue() {
			return Boolean.FALSE;
		}
		
		@Override
		public void set(Object value) {
			if (value != Boolean.TRUE && value != Boolean.FALSE)
				throw new IllegalArgumentException();
			super.set(value);
		}
	};
	
	public static final ThreadLocal overrideThread = new ThreadLocal() {
		@Override
		protected Object initialValue() {
			return Boolean.FALSE;
		}
		@Override
		public void set(Object value) {
			if (value != Boolean.TRUE && value != Boolean.FALSE)
				throw new IllegalArgumentException();
			if (value == Boolean.TRUE
                    && ((Boolean)startupThread.get()).booleanValue()) {
				throw new IllegalStateException();
			}
			super.set(value);
		}
	};
	
    public UISynchronizer(Display display, UILockListener lock) {
        super(display);
        this.lockListener = lock;
        use32Threading = WorkbenchPlugin.getDefault()
				.getPreferenceStore().getBoolean(
						IPreferenceConstants.USE_32_THREADING);
    }
    
    public void started() {
    	synchronized (this) {
			if (!isStarting)
				throw new IllegalStateException();
			isStarting = false;
			for (Iterator i = pendingStartup.iterator(); i.hasNext();) {
				Runnable runnable = (Runnable) i.next();
				try {
					super.asyncExec(runnable);
				} catch (RuntimeException e) {
				}
			}
			pendingStartup = null;
			this.notifyAll();
    	}    	
    }
    
    @Override
	protected void asyncExec(Runnable runnable) {
    	if (runnable != null && !use32Threading) {
			synchronized (this) {
				if (isStarting && !(runnable instanceof StartupRunnable)
						&& overrideThread.get() == Boolean.FALSE) {

					pendingStartup.add(runnable);

					return;
				}
			}
		}
    	super.asyncExec(runnable);
    }

	@Override
	public void syncExec(Runnable runnable) {
		
		synchronized (this) {
			if (isStarting && !use32Threading && startupThread.get() == Boolean.FALSE
					&& overrideThread.get() == Boolean.FALSE) {
				do {
					try {
						this.wait();
					} catch (InterruptedException e) {
					}
				} while (isStarting);
			}
		}
		
        if ((runnable == null) || lockListener.isUI()
                || !lockListener.isLockOwner()) {
            super.syncExec(runnable);
            return;
        }
        Semaphore work = new Semaphore(runnable);
        work.setOperationThread(Thread.currentThread());
        lockListener.addPendingWork(work);
        asyncExec(new Runnable() {
            @Override
			public void run() {
                lockListener.doPendingWork();
            }
        });
        try {
            do {
                if (lockListener.isUIWaiting()) {
					lockListener.interruptUI();
				}
            } while (!work.acquire(1000));
        } catch (InterruptedException e) {
        }
    }
}
