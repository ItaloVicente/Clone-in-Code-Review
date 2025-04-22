	private void setInitialFilterText() {
		filterText.setText(IDEWorkbenchMessages.CleanDialog_typeFilterText);
		filterText.selectAll();
		filterText.setFocus();
	}

	protected void updateToolbar(boolean visible) {
		if (filterToolBar != null) {
			filterToolBar.getControl().setVisible(visible);
		}
	}

