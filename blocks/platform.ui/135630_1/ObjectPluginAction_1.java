		if (activePart != null && part == activePart) {
			clearActivePart();
		}
	}

	private void clearActivePart() {
		selectionChanged(StructuredSelection.EMPTY);
		stopPartListening();
		disposeDelegate();
		activePart = null;
