
package org.eclipse.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Event;

public abstract class BaseSelectionListenerAction extends Action implements
        ISelectionChangedListener {
    private IStructuredSelection selection = new StructuredSelection();

    private boolean running = false;

    private IStructuredSelection deferredSelection = null;

    protected BaseSelectionListenerAction(String text) {
        super(text);
    }

    protected void clearCache() {
    }

    public IStructuredSelection getStructuredSelection() {
        return selection;
    }

    public final void selectionChanged(IStructuredSelection selection) {
        if (running) {
            deferredSelection = selection;
            return;
        }
        this.selection = selection;
        clearCache();
        setEnabled(updateSelection(selection));
    }

    @Override
	public final void selectionChanged(SelectionChangedEvent event) {
        ISelection selection = event.getSelection();
        if (selection instanceof IStructuredSelection) {
			selectionChanged((IStructuredSelection) selection);
		} else {
			selectionChanged(StructuredSelection.EMPTY);
		}
    }

    protected boolean updateSelection(IStructuredSelection selection) {
        return true;
    }

    @Override
	public void runWithEvent(Event event) {
        running = true;
        try {
            run();
        } finally {
            running = false;
            IStructuredSelection s = deferredSelection;
            deferredSelection = null;
            if (s != null) {
                selectionChanged(s);
            }
        }
    }
}
