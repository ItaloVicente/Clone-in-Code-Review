	}

	void handleSelectionChanged(IStructuredSelection selection) {
		openAction.selectionChanged(selection);
		removeAction.selectionChanged(selection);
		editAction.selectionChanged(selection);
		selectAllAction.selectionChanged(selection);
		showInNavigatorAction.selectionChanged(selection);
	}

	@Override
