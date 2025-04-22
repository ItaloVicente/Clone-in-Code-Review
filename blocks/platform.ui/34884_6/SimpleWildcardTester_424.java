package org.eclipse.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;

public abstract class SelectionProviderAction extends Action implements
        ISelectionChangedListener {

    private ISelectionProvider provider;

    protected SelectionProviderAction(ISelectionProvider provider, String text) {
        super(text);
        this.provider = provider;
        provider.addSelectionChangedListener(this);
    }

    public void dispose() {
        provider.removeSelectionChangedListener(this);
    }

    public ISelection getSelection() {
        return provider.getSelection();
    }

    public ISelectionProvider getSelectionProvider() {
        return provider;
    }

    public IStructuredSelection getStructuredSelection() {
        ISelection selection = provider.getSelection();
        if (selection instanceof IStructuredSelection) {
			return (IStructuredSelection) selection;
		} else {
			return new StructuredSelection();
		}
    }

    public void selectionChanged(ISelection selection) {
    }

    public void selectionChanged(IStructuredSelection selection) {
    }

    @Override
	public final void selectionChanged(SelectionChangedEvent event) {
        ISelection selection = event.getSelection();
        if (selection instanceof IStructuredSelection) {
			selectionChanged((IStructuredSelection) selection);
		} else {
			selectionChanged(selection);
		}
    }
}
