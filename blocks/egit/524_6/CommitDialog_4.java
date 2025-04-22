	private void updateChangeIdButton() {
		String curText = commitText.getText();
		if (!curText.endsWith(Text.DELIMITER))
			curText += Text.DELIMITER;

		boolean hasId = curText.indexOf(Text.DELIMITER + "Change-Id: ") != -1; //$NON-NLS-1$
		if (hasId)
			changeIdButton.setSelection(true);
	}

