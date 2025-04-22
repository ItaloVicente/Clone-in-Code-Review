	private void updateButtonsOnSelection() {
		long selectedCount = Arrays.stream(getViewer().getTable().getItems()).filter(TableItem::getChecked).count();
		int totalCount = getViewer().getTable().getItemCount();
		getButton(IDialogConstants.SELECT_ALL_ID).setEnabled(selectedCount < totalCount);
		getButton(IDialogConstants.DESELECT_ALL_ID).setEnabled(selectedCount > 0);
		if (okButtonText != null) {
			getButton(IDialogConstants.OK_ID).setText(
					NLS.bind(selectedCount == 0 && okButtonTextWhenNoSelection != null ? okButtonTextWhenNoSelection
							: okButtonText,
							selectedCount, totalCount));
		}
