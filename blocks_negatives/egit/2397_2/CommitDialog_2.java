	private void updateChangeIdButton() {
		String curText = commitText.getText();
		if (!curText.endsWith(Text.DELIMITER))
			curText += Text.DELIMITER;

		if (hasId) {
			changeIdButton.setSelection(true);
			createChangeId = true;
		}
	}

