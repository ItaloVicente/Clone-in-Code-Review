package org.eclipse.ui.tests.api;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;

public class MockSelectionProvider implements ISelectionProvider {

    private List listeners = new ArrayList(3);

    public void fireSelection() {
        fireSelection(new SelectionChangedEvent(this, StructuredSelection.EMPTY));
    }

    public void fireSelection(SelectionChangedEvent event) {
        Iterator iter = listeners.iterator();
        while (iter.hasNext()) {
            ((ISelectionChangedListener) iter.next()).selectionChanged(event);
        }
    }

    @Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
        listeners.add(listener);
    }

    @Override
	public ISelection getSelection() {
        return StructuredSelection.EMPTY;
    }

    @Override
	public void removeSelectionChangedListener(
            ISelectionChangedListener listener) {
        listeners.remove(listener);
    }

    @Override
	public void setSelection(ISelection selection) {
    }
}

