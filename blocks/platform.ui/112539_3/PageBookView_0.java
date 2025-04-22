
		forwardSelection(new SelectionChangedEvent(provider, StructuredSelection.EMPTY));
	}

	private void forwardSelection(SelectionChangedEvent event) {
		getSelectionProvider().selectionChanged(event);
		getSelectionProvider().postSelectionChanged(event);
