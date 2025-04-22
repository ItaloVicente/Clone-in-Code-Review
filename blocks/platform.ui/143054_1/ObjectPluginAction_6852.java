	private void clearActivePart() {
		selectionChanged(StructuredSelection.EMPTY);
		stopPartListening();
		disposeDelegate();
		activePart = null;
	}

