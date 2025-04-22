        return site;
    }

    public void setTitle(String newTitle) {
        title = newTitle;
        firePropertyChange(IWorkbenchPartConstants.PROP_TITLE);
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IViewPart#init(org.eclipse.ui.IViewSite)
     */
    @Override
	public void init(IViewSite site) throws PartInitException {
        this.site = site;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IViewPart#init(org.eclipse.ui.IViewSite, org.eclipse.ui.IMemento)
     */
    @Override
	public void init(IViewSite site, IMemento memento) throws PartInitException {
        this.site = site;
    }

    /**
     * Fires a property changed event.
     *
     * @param propertyId the id of the property that changed
     */
    protected void firePropertyChange(final int propertyId) {
        Object[] array = getListeners();
        for (int nX = 0; nX < array.length; nX++) {
            final IPropertyListener l = (IPropertyListener) array[nX];
            SafeRunner.run(new SafeRunnable() {
                @Override
