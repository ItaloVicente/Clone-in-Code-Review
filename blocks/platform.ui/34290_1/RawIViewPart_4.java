		return site;
	}

	public void setTitle(String newTitle) {
		title = newTitle;
		firePropertyChange(IWorkbenchPartConstants.PROP_TITLE);
	}

	@Override
	public void init(IViewSite site) {
		this.site = site;
	}

	@Override
	public void init(IViewSite site, IMemento memento) {
		this.site = site;
	}

	protected void firePropertyChange(final int propertyId) {
		Object[] array = getListeners();
		for (int nX = 0; nX < array.length; nX++) {
			final IPropertyListener l = (IPropertyListener) array[nX];
			SafeRunner.run(new SafeRunnable() {
				@Override
