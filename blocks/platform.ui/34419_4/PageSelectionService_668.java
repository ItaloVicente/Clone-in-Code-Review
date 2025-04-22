package org.eclipse.ui.internal;

import org.eclipse.jface.viewers.IPostSelectionProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveListener2;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;

public class PagePartSelectionTracker extends AbstractPartSelectionTracker
        implements IPartListener, IPerspectiveListener2, ISelectionChangedListener {

    private IWorkbenchPage fPage;

    private IWorkbenchPart fPart;

    private ISelectionChangedListener selectionListener = new ISelectionChangedListener() {
        @Override
		public void selectionChanged(SelectionChangedEvent event) {
            fireSelection(getPart(), event.getSelection());
        }
    };

    private ISelectionChangedListener postSelectionListener = new ISelectionChangedListener() {
        @Override
		public void selectionChanged(SelectionChangedEvent event) {
            firePostSelection(getPart(), event.getSelection());
        }
    };

    public PagePartSelectionTracker(IWorkbenchPage page, String partId) {
        super(partId);
        setPage(page);
        page.addPartListener(this);
        page.getWorkbenchWindow().addPerspectiveListener(this);
        String secondaryId = null;
        int indexOfColon;
        if ((indexOfColon = partId.indexOf(':')) != -1) {
        	secondaryId = partId.substring(indexOfColon + 1);
        	partId = partId.substring(0, indexOfColon);
        }
		IViewReference part = page.findViewReference(partId, secondaryId);
        if (part != null && part.getView(false) != null) {
            setPart(part.getView(false), false);
        }
    }

    @Override
	public void dispose() {
    	IWorkbenchPage page = getPage();
    	page.getWorkbenchWindow().removePerspectiveListener(this);
    	page.removePartListener(this);
        setPart(null, false);
        setPage(null);
        super.dispose();
    }

    @Override
	public void partActivated(IWorkbenchPart part) {
    }

    @Override
	public void partBroughtToTop(IWorkbenchPart part) {
    }

    @Override
	public void partClosed(IWorkbenchPart part) {
        if (getPartId(part).equals(getPartId())) {
            setPart(null, true);
        }
    }

    @Override
	public void partDeactivated(IWorkbenchPart part) {
    }

    @Override
	public void partOpened(IWorkbenchPart part) {
        if (getPartId(part).equals(getPartId())) {
            setPart(part, true);
        }
    }

    private Object getPartId(IWorkbenchPart part) {
        String id = part.getSite().getId();
        if (part instanceof IViewPart) {
            String secondaryId = ((IViewPart) part).getViewSite()
                    .getSecondaryId();
            if (secondaryId != null) {
                id = id + ':' + secondaryId;
            }
        }
        return id;
    }

    @Override
	public void selectionChanged(SelectionChangedEvent event) {
        fireSelection(getPart(), event.getSelection());
    }

    private void setPage(IWorkbenchPage page) {
        fPage = page;
    }

    protected IWorkbenchPage getPage() {
        return fPage;
    }

    protected IWorkbenchPart getPart() {
        return fPart;
    }

    @Override
	public ISelection getSelection() {
        IWorkbenchPart part = getPart();
        if (part != null) {
            ISelectionProvider sp = part.getSite().getSelectionProvider();
            if (sp != null) {
                return sp.getSelection();
            }
        }
        return null;
    }

    protected ISelectionProvider getSelectionProvider() {
        IWorkbenchPart part = getPart();
        if (part != null) {
            return part.getSite().getSelectionProvider();
        }
        return null;
    }

    private void setPart(IWorkbenchPart part, boolean notify) {
        if (fPart != null) {
            ISelectionProvider sp = fPart.getSite().getSelectionProvider();
            if (sp != null) {
                sp.removeSelectionChangedListener(selectionListener);
                if (sp instanceof IPostSelectionProvider) {
					((IPostSelectionProvider) sp)
                            .removePostSelectionChangedListener(postSelectionListener);
				} else {
					sp.removeSelectionChangedListener(postSelectionListener);
				}
            }
        }
        fPart = part;
        ISelection sel = null;
        if (part != null) {
            ISelectionProvider sp = part.getSite().getSelectionProvider();
            if (sp != null) {
                sp.addSelectionChangedListener(selectionListener);
                if (sp instanceof IPostSelectionProvider) {
					((IPostSelectionProvider) sp)
                            .addPostSelectionChangedListener(postSelectionListener);
				} else {
					sp.addSelectionChangedListener(postSelectionListener);
				}
                if (notify) {
                    sel = sp.getSelection();
                }
            }
        }
        if (notify) {
            fireSelection(part, sel);
            firePostSelection(part, sel);
        }
    }

	@Override
	public void perspectiveActivated(IWorkbenchPage page, IPerspectiveDescriptor perspective) {
	}

	@Override
	public void perspectiveChanged(IWorkbenchPage page, IPerspectiveDescriptor perspective, String changeId) {
	}

	@Override
	public void perspectiveChanged(IWorkbenchPage page, IPerspectiveDescriptor perspective,
			IWorkbenchPartReference partRef, String changeId) {
		if (partRef == null)
			return;
		IWorkbenchPart part = partRef.getPart(false);
		if (part == null)
			return;
		if (IWorkbenchPage.CHANGE_VIEW_SHOW.equals(changeId)) {
			if (getPart() != null) // quick check first, plus avoids double setting
				return;
	        if (getPartId(part).equals(getPartId()))
	            setPart(part, true);
		}
	}
}
