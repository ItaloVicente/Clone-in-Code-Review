package org.eclipse.ui.views.contentoutline;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.part.Page;

public abstract class ContentOutlinePage extends Page implements
        IContentOutlinePage, ISelectionChangedListener {
    private ListenerList selectionChangedListeners = new ListenerList();

    private TreeViewer treeViewer;

    protected ContentOutlinePage() {
        super();
    }

    @Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
        selectionChangedListeners.add(listener);
    }

    @Override
	public void createControl(Composite parent) {
        treeViewer = new TreeViewer(parent, getTreeStyle());
        treeViewer.addSelectionChangedListener(this);
    }
    
	protected int getTreeStyle() {
		return SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL;
	}

    protected void fireSelectionChanged(ISelection selection) {
        final SelectionChangedEvent event = new SelectionChangedEvent(this,
                selection);

        Object[] listeners = selectionChangedListeners.getListeners();
        for (int i = 0; i < listeners.length; ++i) {
            final ISelectionChangedListener l = (ISelectionChangedListener) listeners[i];
            SafeRunner.run(new SafeRunnable() {
                @Override
				public void run() {
                    l.selectionChanged(event);
                }
            });
        }
    }

    @Override
	public Control getControl() {
        if (treeViewer == null) {
			return null;
		}
        return treeViewer.getControl();
    }

    @Override
	public ISelection getSelection() {
        if (treeViewer == null) {
			return StructuredSelection.EMPTY;
		}
        return treeViewer.getSelection();
    }

    protected TreeViewer getTreeViewer() {
        return treeViewer;
    }

    @Override
	public void init(IPageSite pageSite) {
        super.init(pageSite);
        pageSite.setSelectionProvider(this);
    }

    @Override
	public void removeSelectionChangedListener(
            ISelectionChangedListener listener) {
        selectionChangedListeners.remove(listener);
    }

    @Override
	public void selectionChanged(SelectionChangedEvent event) {
        fireSelectionChanged(event.getSelection());
    }

    @Override
	public void setFocus() {
        treeViewer.getControl().setFocus();
    }

    @Override
	public void setSelection(ISelection selection) {
        if (treeViewer != null) {
			treeViewer.setSelection(selection);
		}
    }
}
