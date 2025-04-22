		selectionChanged(new SelectionChangedEvent(event.getViewer(), event.getViewer().getSelection()));
		getContainer().showPage(getNextPage());
	}

	private void layoutTopControl(Control control) {
		GridData data = new GridData(GridData.FILL_BOTH);

		int availableRows = DialogUtil.availableRows(control.getParent());

		if (availableRows > 50) {
			data.heightHint = SIZING_LISTS_HEIGHT;
		} else {
			data.heightHint = availableRows * 3;
		}

		control.setLayoutData(data);

	}

	private void restoreWidgetValues() {

		IDialogSettings settings = getDialogSettings();
		if (settings == null) {
