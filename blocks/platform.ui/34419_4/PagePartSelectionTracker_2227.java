package org.eclipse.ui.internal;

import org.eclipse.core.commands.common.EventManager;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.ui.IPageListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.internal.misc.UIStats;

public class PageListenerList extends EventManager {

    public PageListenerList() {
        super();
    }

    public void addPageListener(IPageListener l) {
    	addListenerObject(l);
    }

    private void fireEvent(SafeRunnable runnable, IPageListener listener, IWorkbenchPage page, String description) {
    	String label = null;//for debugging
    	if (UIStats.isDebugging(UIStats.NOTIFY_PAGE_LISTENERS)) {
    		label = description + page.getLabel();
    		UIStats.start(UIStats.NOTIFY_PAGE_LISTENERS, label);
    	}
    	SafeRunner.run(runnable);
    	if (UIStats.isDebugging(UIStats.NOTIFY_PAGE_LISTENERS)) {
			UIStats.end(UIStats.NOTIFY_PAGE_LISTENERS, listener, label);
		}
	}

    public void firePageActivated(final IWorkbenchPage page) {
        Object[] array = getListeners();
        for (int i = 0; i < array.length; i++) {
            final IPageListener l = (IPageListener) array[i];
            fireEvent(new SafeRunnable() {
                @Override
				public void run() {
                    l.pageActivated(page);
                }
            }, l, page, "activated::"); //$NON-NLS-1$
        }
    }

    public void firePageClosed(final IWorkbenchPage page) {
        Object[] array = getListeners();
        for (int i = 0; i < array.length; i++) {
            final IPageListener l = (IPageListener) array[i];
            fireEvent(new SafeRunnable() {
                @Override
				public void run() {
                    l.pageClosed(page);
                }
            }, l, page, "closed::"); //$NON-NLS-1$
        }
    }

    public void firePageOpened(final IWorkbenchPage page) {
        Object[] listeners = getListeners();
        for (int i = 0; i < listeners.length; i++) {
            final IPageListener l = (IPageListener) listeners[i];
            fireEvent(new SafeRunnable() {
                @Override
				public void run() {
                    l.pageOpened(page);
                }
            }, l, page, "opened::"); //$NON-NLS-1$
        }
    }

    public void removePageListener(IPageListener l) {
        removeListenerObject(l);
    }
}
