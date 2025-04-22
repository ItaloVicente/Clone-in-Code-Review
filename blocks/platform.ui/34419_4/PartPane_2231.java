package org.eclipse.ui.internal;

import org.eclipse.core.commands.common.EventManager;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.internal.misc.UIStats;

public class PartListenerList2 extends EventManager {

    public PartListenerList2() {
        super();
    }

    public void addPartListener(IPartListener2 l) {
        addListenerObject(l);
    }

    private void fireEvent(SafeRunnable runnable, IPartListener2 listener, IWorkbenchPartReference ref, String string) {
    	String label = null;//for debugging
    	if (UIStats.isDebugging(UIStats.NOTIFY_PART_LISTENERS)) {
    		label = string + ref.getTitle();
    		UIStats.start(UIStats.NOTIFY_PART_LISTENERS, label);
    	}
    	SafeRunner.run(runnable);
    	if (UIStats.isDebugging(UIStats.NOTIFY_PART_LISTENERS)) {
			UIStats.end(UIStats.NOTIFY_PART_LISTENERS, listener, label);
		}
	}

    public void firePartActivated(final IWorkbenchPartReference ref) {
        Object[] array = getListeners();
        for (int i = 0; i < array.length; i++) {
            final IPartListener2 l = (IPartListener2) array[i];
            fireEvent(new SafeRunnable() {
                @Override
				public void run() {
                    l.partActivated(ref);
                }
            }, l, ref, "activated::"); //$NON-NLS-1$
        }
    }

    public void firePartBroughtToTop(final IWorkbenchPartReference ref) {
        Object[] array = getListeners();
        for (int i = 0; i < array.length; i++) {
            final IPartListener2 l = (IPartListener2) array[i];
            fireEvent(new SafeRunnable() {
                @Override
				public void run() {
                    l.partBroughtToTop(ref);
                }
            }, l, ref, "broughtToTop::"); //$NON-NLS-1$
        }
    }

    public void firePartClosed(final IWorkbenchPartReference ref) {
        Object[] array = getListeners();
        for (int i = 0; i < array.length; i++) {
            final IPartListener2 l = (IPartListener2) array[i];
            fireEvent(new SafeRunnable() {
                @Override
				public void run() {
                    l.partClosed(ref);
                }
            }, l, ref, "closed::"); //$NON-NLS-1$
        }
    }

    public void firePartDeactivated(final IWorkbenchPartReference ref) {
        Object[] array = getListeners();
        for (int i = 0; i < array.length; i++) {
            final IPartListener2 l = (IPartListener2) array[i];
            fireEvent(new SafeRunnable() {
                @Override
				public void run() {
                    l.partDeactivated(ref);
                }
            }, l, ref, "deactivated::"); //$NON-NLS-1$
        }
    }

    public void firePartOpened(final IWorkbenchPartReference ref) {
        Object[] array = getListeners();
        for (int i = 0; i < array.length; i++) {
            final IPartListener2 l = (IPartListener2) array[i];
            fireEvent(new SafeRunnable() {
                @Override
				public void run() {
                    l.partOpened(ref);
                }
            }, l, ref, "opened::"); //$NON-NLS-1$
        }
    }

    public void firePartHidden(final IWorkbenchPartReference ref) {
        Object[] array = getListeners();
        for (int i = 0; i < array.length; i++) {
            final IPartListener2 l;
            if (array[i] instanceof IPartListener2) {
				l = (IPartListener2) array[i];
			} else {
				continue;
			}

            fireEvent(new SafeRunnable() {
                @Override
				public void run() {
                    l.partHidden(ref);
                }
            }, l, ref, "hidden::"); //$NON-NLS-1$
        }
    }

    public void firePartVisible(final IWorkbenchPartReference ref) {
        Object[] array = getListeners();
        for (int i = 0; i < array.length; i++) {
            final IPartListener2 l;
            if (array[i] instanceof IPartListener2) {
				l = (IPartListener2) array[i];
			} else {
				continue;
			}

            fireEvent(new SafeRunnable() {
                @Override
				public void run() {
                    l.partVisible(ref);
                }
            }, l, ref, "visible::"); //$NON-NLS-1$
        }
    }

    public void firePartInputChanged(final IWorkbenchPartReference ref) {
        Object[] array = getListeners();
        for (int i = 0; i < array.length; i++) {
            final IPartListener2 l;
            if (array[i] instanceof IPartListener2) {
				l = (IPartListener2) array[i];
			} else {
				continue;
			}

            fireEvent(new SafeRunnable() {
                @Override
				public void run() {
                    l.partInputChanged(ref);
                }
            }, l, ref, "inputChanged::"); //$NON-NLS-1$
        }
    }
    
    public void removePartListener(IPartListener2 l) {
        removeListenerObject(l);
    }

	public void firePageChanged(final PageChangedEvent event) {
		Object[] array = getListeners();
        for (int i = 0; i < array.length; i++) {
            final IPageChangedListener l;
            if (array[i] instanceof IPageChangedListener) {
				l = (IPageChangedListener) array[i];
			} else {
				continue;
			}

            SafeRunnable.run(new SafeRunnable() {
                @Override
				public void run() {
                    l.pageChanged(event);
                }
            });
        }
	}
}
