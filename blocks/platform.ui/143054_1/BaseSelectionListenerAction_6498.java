	public final void selectionChanged(IStructuredSelection selection) {
		if (running) {
			deferredSelection = selection;
			return;
		}
		this.selection = selection;
		clearCache();
		setEnabled(updateSelection(selection));
	}
