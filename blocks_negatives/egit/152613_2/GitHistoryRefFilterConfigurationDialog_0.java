		RefFilter filter = getSelectionFromTable();
		boolean writableRow = filter != null && !filter.isPreconfigured();
		removeButton.setEnabled(writableRow);
		editButton.setEnabled(writableRow);
	}

	private RefFilter getSelectionFromTable() {
