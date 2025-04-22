	private void setInitialFilterText() {
		filterText.setText(IDEWorkbenchMessages.CleanDialog_typeFilterText);
		filterText.selectAll();
		filterText.setFocus();
	}

	protected void updateClearButton(boolean visible) {
		if (clearButtonControl != null) {
			clearButtonControl.setVisible(visible);
		}
	}

