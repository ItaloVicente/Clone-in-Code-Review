	private int findOffsetOfChangeIdLine(String message) {
	}

	private void updateSignedOffButton() {
		String curText = commitText.getText();
		if (!curText.endsWith(Text.DELIMITER))
			curText += Text.DELIMITER;

		signedOffButton.setSelection(curText.indexOf(getSignedOff() + Text.DELIMITER) != -1);
