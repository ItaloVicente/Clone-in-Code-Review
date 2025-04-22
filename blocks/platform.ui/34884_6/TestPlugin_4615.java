package org.eclipse.ui.tests;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;


public class SelectionProviderView extends ViewPart implements
        ISelectionProvider {
    public final static String ID = "org.eclipse.ui.tests.SelectionProviderView";

    public final static String ID_2 = "org.eclipse.ui.tests.SelectionProviderView2";

    private ListenerList selectionChangedListeners = new ListenerList();

    private ISelection lastSelection = StructuredSelection.EMPTY;

    private Text text;

    public SelectionProviderView() {
        super();
    }

    @Override
	public void setFocus() {
        text.setFocus();
    }

    @Override
	public void createPartControl(Composite parent) {
        text = new Text(parent, SWT.MULTI | SWT.WRAP);
        text.setLayoutData(new GridData(GridData.FILL_BOTH));
    }

    @Override
	public void init(IViewSite site) throws PartInitException {
        site.setSelectionProvider(this);
        super.init(site);
    }

    @Override
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
        selectionChangedListeners.add(listener);
    }

    @Override
	public ISelection getSelection() {
        return lastSelection;
    }

    @Override
	public void removeSelectionChangedListener(
            ISelectionChangedListener listener) {
        selectionChangedListeners.remove(listener);
    }

    public void setSelection(Object obj) {
        setSelection(new StructuredSelection(obj));
    }

    @Override
	public void setSelection(ISelection selection) {
        lastSelection = selection;

        SelectionChangedEvent event = new SelectionChangedEvent(this, selection);

        text.setText(selection.toString());

        Object[] listeners = selectionChangedListeners.getListeners();
        for (int i = 0; i < listeners.length; ++i) {
            ((ISelectionChangedListener) listeners[i]).selectionChanged(event);
        }
    }
}
