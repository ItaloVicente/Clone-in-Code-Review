		}
	}

	private boolean updateHintMessage() {
		if (hintMessage != null) {
			hintLabel.showMessage(hintMessage);
			return true;
		} else {
			boolean redraw = hintLabel.getVisible();
			hintLabel.hideMessage();
			return redraw;
