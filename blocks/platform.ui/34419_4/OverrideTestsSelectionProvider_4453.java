package org.eclipse.ui.tests.views.properties.tabbed.override;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.ui.tests.views.properties.tabbed.model.Element;

public class OverrideTestsSelectionProvider implements ISelectionProvider {

	private ListenerList selectionChangedListeners = new ListenerList();

	private final TableViewer viewer;

	OverrideTestsSelectionProvider(TableViewer aViewer) {
		this.viewer = aViewer;
	}

	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		selectionChangedListeners.add(listener);
	}

	public ISelection getSelection() {
		ISelection selection = viewer.getSelection();
		IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		if (structuredSelection.isEmpty()) {
			return new OverrideTestsSelection(null);
		}
		Element element = (Element) structuredSelection.getFirstElement();
		return new OverrideTestsSelection(element);
	}

	public void removeSelectionChangedListener(
			ISelectionChangedListener listener) {
		selectionChangedListeners.remove(listener);
	}

	public void selectionChanged(final SelectionChangedEvent event) {
		Object[] listeners = selectionChangedListeners.getListeners();
		for (int i = 0; i < listeners.length; ++i) {
			final ISelectionChangedListener l = (ISelectionChangedListener) listeners[i];
			l.selectionChanged(event);
		}
	}

	public void setSelection(ISelection selection) {
		viewer.setSelection(selection);
	}
}
