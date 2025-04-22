package org.eclipse.ui.views.markers.internal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;

class MarkerSelectionProviderAdapter implements ISelectionProvider {

    List listeners = new ArrayList();

    ISelection theSelection = null;

    @Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
        listeners.add(listener);
    }

    @Override
	public ISelection getSelection() {
        return theSelection;
    }

    @Override
	public void removeSelectionChangedListener(
            ISelectionChangedListener listener) {
        listeners.remove(listener);
    }

    @Override
	public void setSelection(ISelection selection) {
        theSelection = selection;
        final SelectionChangedEvent e = new SelectionChangedEvent(this, selection);
        Object[] listenersArray = listeners.toArray();
        
        for (int i = 0; i < listenersArray.length; i++) {
            final ISelectionChangedListener l = (ISelectionChangedListener) listenersArray[i];
            SafeRunner.run(new SafeRunnable() {
                @Override
				public void run() {
                    l.selectionChanged(e);
                }
            });
		}
    }

}
