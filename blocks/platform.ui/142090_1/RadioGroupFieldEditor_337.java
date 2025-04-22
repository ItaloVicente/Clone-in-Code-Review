		Control control = getLabelControl();
		if (control != null) {
			((GridData) control.getLayoutData()).horizontalSpan = numColumns;
		}
		((GridData) radioBox.getLayoutData()).horizontalSpan = numColumns;
	}

	private boolean checkArray(String[][] table) {
		if (table == null) {
