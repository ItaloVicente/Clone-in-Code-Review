	private void showSelectionAndDescription() {
		selectionUpdatePending = false;
		if (currentPart == null || currentSelection == null) {
			return;
		}
		IPropertySheetPage page = (IPropertySheetPage) getCurrentPage();
		if (page != null) {
			page.selectionChanged(currentPart, currentSelection);
		}
		updateContentDescription();
	}

