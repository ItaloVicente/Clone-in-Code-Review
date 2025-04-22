	private void updateButtonsEnablement() {
		if (updateButtonsEnablement) {
			int totalCount = getViewer().getTable().getItems().length;
			int checkedCount = getViewer().getCheckedElements().length;
			updateButtonsEnablement(totalCount, checkedCount);
		}
	}
