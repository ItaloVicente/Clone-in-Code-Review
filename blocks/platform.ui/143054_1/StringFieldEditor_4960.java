		GridData gd = (GridData) textField.getLayoutData();
		gd.horizontalSpan = numColumns - 1;
		gd.grabExcessHorizontalSpace = gd.horizontalSpan == 1;
	}

	protected boolean checkState() {
		boolean result = false;
		if (emptyStringAllowed) {
