	private void editCurrentRow() {
		editFilter((RefFilter) configsTable.getStructuredSelection()
				.getFirstElement());
	}

	private void editFilter(RefFilter filter) {
		editingMode = true;
		configsTable.editElement(filter, 0);
		updateButtonEnablement();
	}

