package org.eclipse.ui.internal;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.INullSelectionListener;
import org.eclipse.ui.IPageListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;

public class WindowPartSelectionTracker extends AbstractPartSelectionTracker
        implements IPageListener {
    private IWorkbenchWindow fWindow;

    private final INullSelectionListener selListener = new INullSelectionListener() {
        @Override
		public void selectionChanged(IWorkbenchPart part, ISelection selection) {
            fireSelection(part, selection);
        }
    };

    private final INullSelectionListener postSelListener = new INullSelectionListener() {
        @Override
		public void selectionChanged(IWorkbenchPart part, ISelection selection) {
            firePostSelection(part, selection);
        }
    };

    public WindowPartSelectionTracker(IWorkbenchWindow window, String partId) {
        super(partId);
        setWindow(window);
        window.addPageListener(this);
        IWorkbenchPage[] pages = window.getPages();
        for (int i = 0; i < pages.length; i++) {
            pageOpened(pages[i]);
        }
    }

    @Override
	public void pageActivated(IWorkbenchPage page) {
    }

    @Override
	public void pageClosed(IWorkbenchPage page) {
        page.removeSelectionListener(getPartId(), selListener);
        page.removePostSelectionListener(getPartId(), postSelListener);
    }

    @Override
	public void pageOpened(IWorkbenchPage page) {
        page.addSelectionListener(getPartId(), selListener);
        page.addPostSelectionListener(getPartId(), postSelListener);
    }

    private void setWindow(IWorkbenchWindow window) {
        fWindow = window;
    }

    protected IWorkbenchWindow getWindow() {
        return fWindow;
    }

    @Override
	public void dispose() {
        super.dispose();
        fWindow = null;
    }

    @Override
	public ISelection getSelection() {
        IWorkbenchPage page = getWindow().getActivePage();
        if (page != null) {
            return page.getSelection(getPartId());
        }
        return null;
    }
}
