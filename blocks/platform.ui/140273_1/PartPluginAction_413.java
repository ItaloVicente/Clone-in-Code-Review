	protected void registerSelectionListener(IWorkbenchPart aPart) {
		ISelectionProvider selectionProvider = aPart.getSite().getSelectionProvider();
		if (selectionProvider != null) {
			selectionProvider.addSelectionChangedListener(this);
			selectionChanged(selectionProvider.getSelection());
		}
	}
