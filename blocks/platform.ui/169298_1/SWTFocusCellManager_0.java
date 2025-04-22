	void setFocusCell(ViewerCell focusCell) {
		setFocusCell(focusCell, null);
	}

	protected void scrollIntoView(ViewerCell focusCell, Event event) {
		if (focusCell != null) {
			focusCell.scrollIntoView();
		}
	}

