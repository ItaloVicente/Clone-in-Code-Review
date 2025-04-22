	private void reactOnInitialSelection() {
		StructuredSelection sel = null;
		if (initialSelection instanceof StructuredSelection) {
			sel = (StructuredSelection) initialSelection;
		} else if (initialSelection != null && !initialSelection.isEmpty()) {
			sel = getSelectionOfActiveEditor();
		}
		if (sel != null) {
			reactOnSelection(sel);
		}
		initialSelection = null;
	}

