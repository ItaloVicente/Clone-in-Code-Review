	private void updateMessage() {
		String message = commitMessageComponent.getMessage();
		boolean needsRedraw = false;
		if (message != null) {
			warningLabel.showMessage(message);
			needsRedraw = true;
		} else {
			needsRedraw = warningLabel.isVisible();
			warningLabel.hideMessage();
		}
		if (needsRedraw)
			commitMessageSection.redraw();
	}

