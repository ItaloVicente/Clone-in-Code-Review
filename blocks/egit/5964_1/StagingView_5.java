	private void updateMessage() {
		String message = commitMessageComponent.getMessage();
		if (message != null)
			warningLabel.showMessage(message);
		else
			warningLabel.hideMessage();
	}

