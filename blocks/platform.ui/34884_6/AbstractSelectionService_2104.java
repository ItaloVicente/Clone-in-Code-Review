package org.eclipse.ui.internal;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.INullSelectionListener;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;

public abstract class AbstractPartSelectionTracker {
    private ListenerList fListeners = new ListenerList();

    private ListenerList postListeners = new ListenerList();

    private String fPartId;

    public AbstractPartSelectionTracker(String partId) {
        setPartId(partId);
    }

    public void addSelectionListener(ISelectionListener listener) {
        fListeners.add(listener);
    }

    public void addPostSelectionListener(ISelectionListener listener) {
        postListeners.add(listener);
    }

    public abstract ISelection getSelection();

    public void removeSelectionListener(ISelectionListener listener) {
        fListeners.remove(listener);
    }

    public void removePostSelectionListener(ISelectionListener listener) {
        postListeners.remove(listener);
    }

    public void dispose() {
        synchronized (fListeners) {
            Object[] listeners = fListeners.getListeners();
            for (int i = 0; i < listeners.length; i++) {
                fListeners.remove(listeners[i]);
                postListeners.remove(listeners[i]);
            }
        }
    }

    protected void fireSelection(final IWorkbenchPart part, final ISelection sel) {
        Object[] array = fListeners.getListeners();
        for (int i = 0; i < array.length; i++) {
            final ISelectionListener l = (ISelectionListener) array[i];
            if ((part != null && sel != null)
                    || l instanceof INullSelectionListener) {
                SafeRunner.run(new SafeRunnable() {
                    @Override
					public void run() {
                        l.selectionChanged(part, sel);
                    }
                });
            }
        }
    }

    protected void firePostSelection(final IWorkbenchPart part,
            final ISelection sel) {
        Object[] array = postListeners.getListeners();
        for (int i = 0; i < array.length; i++) {
            final ISelectionListener l = (ISelectionListener) array[i];
            if ((part != null && sel != null)
                    || l instanceof INullSelectionListener) {
                SafeRunner.run(new SafeRunnable() {
                    @Override
					public void run() {
                        l.selectionChanged(part, sel);
                    }
                });
            }
        }
    }

    private void setPartId(String partId) {
        fPartId = partId;
    }

    protected String getPartId() {
        return fPartId;
    }

}
