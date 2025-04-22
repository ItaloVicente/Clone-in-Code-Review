package org.eclipse.ui.internal;

import org.eclipse.core.commands.common.EventManager;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.misc.UIStats;

public class PartListenerList extends EventManager {

    public PartListenerList() {
        super();
    }

    public void addPartListener(IPartListener l) {
        addListenerObject(l);
    }

    private void fireEvent(SafeRunnable runnable, IPartListener listener, IWorkbenchPart part, String description) {
    	String label = null;//for debugging
    	if (UIStats.isDebugging(UIStats.NOTIFY_PART_LISTENERS)) {
    		label = description + part.getTitle();
    		UIStats.start(UIStats.NOTIFY_PART_LISTENERS, label);
    	}
    	SafeRunner.run(runnable);
    	if (UIStats.isDebugging(UIStats.NOTIFY_PART_LISTENERS)) {
			UIStats.end(UIStats.NOTIFY_PART_LISTENERS, listener, label);
		}
	}

    public void firePartActivated(final IWorkbenchPart part) {
        Object[] array = getListeners();
        for (int i = 0; i < array.length; i++) {
            final IPartListener l = (IPartListener) array[i];
            fireEvent(new SafeRunnable() {
                @Override
				public void run() {
                    l.partActivated(part);
                }
            }, l, part, "activated::"); //$NON-NLS-1$
        }
    }

    public void firePartBroughtToTop(final IWorkbenchPart part) {
        Object[] array = getListeners();
        for (int i = 0; i < array.length; i++) {
            final IPartListener l = (IPartListener) array[i];
            fireEvent(new SafeRunnable() {
                @Override
				public void run() {
                    l.partBroughtToTop(part);
                }
            }, l, part, "broughtToTop::"); //$NON-NLS-1$
        }
    }

    public void firePartClosed(final IWorkbenchPart part) {
        Object[] array = getListeners();
        for (int i = 0; i < array.length; i++) {
            final IPartListener l = (IPartListener) array[i];
            fireEvent(new SafeRunnable() {
                @Override
				public void run() {
                    l.partClosed(part);
                }
            }, l, part, "closed::"); //$NON-NLS-1$
        }
    }

    public void firePartDeactivated(final IWorkbenchPart part) {
        Object[] array = getListeners();
        for (int i = 0; i < array.length; i++) {
            final IPartListener l = (IPartListener) array[i];
            fireEvent(new SafeRunnable() {
                @Override
				public void run() {
                    l.partDeactivated(part);
                }
            }, l, part, "deactivated::"); //$NON-NLS-1$
        }
    }

    public void firePartOpened(final IWorkbenchPart part) {
        Object[] array = getListeners();
        for (int i = 0; i < array.length; i++) {
            final IPartListener l = (IPartListener) array[i];
            fireEvent(new SafeRunnable() {
                @Override
				public void run() {
                    l.partOpened(part);
                }
            }, l, part, "opened::"); //$NON-NLS-1$
        }
    }

    public void removePartListener(IPartListener l) {
        removeListenerObject(l);
    }
}
