		if (rootEntry != null) {
			updateChildrenOf(rootEntry, tree);
		}
	}

	void removeActivationListener(ICellEditorActivationListener listener) {
		activationListeners.remove(listener);
	}

	private void removeItem(TreeItem item) {
		Object data = item.getData();
		if (data instanceof IPropertySheetEntry) {
