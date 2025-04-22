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

    /**
     * The selection has changed in the part being tracked.
     * Forward it to the listeners.
     *
     * @see ISelectionChangedListener#selectionChanged
     */
    @Override
	public void selectionChanged(SelectionChangedEvent event) {
        fireSelection(getPart(), event.getSelection());
    }

    /**
     * Sets the page this selection provider works for
     *
     * @param page workbench page
     */
    private void setPage(IWorkbenchPage page) {
        fPage = page;
    }

    /**
     * Returns the page this selection provider works for
     *
     * @return workbench page
     */
    protected IWorkbenchPage getPage() {
        return fPage;
    }

    /**
     * Returns the part this is tracking,
     * or <code>null</code> if it is not open
     *
     * @return part., or <code>null</code>
     */
    protected IWorkbenchPart getPart() {
        return fPart;
    }

    /*
     * @see AbstractPartSelectionTracker#getSelection()
     */
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

    /**
     * @see AbstractDebugSelectionProvider#getSelectionProvider()
     */
    protected ISelectionProvider getSelectionProvider() {
        IWorkbenchPart part = getPart();
        if (part != null) {
            return part.getSite().getSelectionProvider();
        }
        return null;
    }

    /**
     * Sets the part for this selection tracker.
     *
     * @param part the part
     * @param notify whether to send notification that the selection has changed.
     */
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
				return;
	        if (getPartId(part).equals(getPartId()))
	            setPart(part, true);
