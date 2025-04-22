	protected void unregisterSelectionListener(IWorkbenchPart aPart) {
		IWorkbenchPartSite site = aPart.getSite();
		if (site == null) {
			return;
		}
		ISelectionProvider selectionProvider = site.getSelectionProvider();
		if (selectionProvider != null) {
			selectionProvider.removeSelectionChangedListener(this);
		}
	}
