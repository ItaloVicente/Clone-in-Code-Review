	private void setAllChecked(boolean state) {
		listViewer.setAllChecked(state);
		updateButtonsOnSelection();
	}

	private void updateButtonsOnSelection() {
		int selectedCount = getViewer().getCheckedElements().length;
		int totalCount = getViewer().getTable().getItems().length;
		getButton(IDialogConstants.SELECT_ALL_ID).setEnabled(selectedCount < totalCount);
		getButton(IDialogConstants.DESELECT_ALL_ID).setEnabled(selectedCount > 0);
		if (okButtonLabelWhenNoSelection != null && okButtonLabelWhenAnySelection != null) {
			getButton(IDialogConstants.OK_ID).setText(
					NLS.bind(selectedCount == 0 ? okButtonLabelWhenNoSelection : okButtonLabelWhenAnySelection,
							selectedCount, totalCount));
		}
