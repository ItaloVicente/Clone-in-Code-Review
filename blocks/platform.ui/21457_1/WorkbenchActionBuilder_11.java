	}

	void updateModeLine(final String text) {
		statusLineItem.setText(text);
	}

	public boolean isApplicationMenu(String menuId) {
		if (menuId.equals(IWorkbenchActionConstants.M_FILE)) {
