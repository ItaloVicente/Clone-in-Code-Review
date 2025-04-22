		return changeButton.getShell();
	}

	public void setChangeButtonText(String text) {
		Assert.isNotNull(text);
		changeButtonText = text;
		if (changeButton != null) {
