	private String getLastLine(String input) {
		String output = input;
		int breakLength = Text.DELIMITER.length();

		int lastIndexOfLineBreak = output.lastIndexOf(Text.DELIMITER);
		if (lastIndexOfLineBreak != -1 && lastIndexOfLineBreak == output.length() - breakLength)
			output = output.substring(0, output.length() - breakLength);

		lastIndexOfLineBreak = output.lastIndexOf(Text.DELIMITER);
		return lastIndexOfLineBreak == -1 ? output : output.substring(lastIndexOfLineBreak + breakLength, output.length());
	}

	private void updateSignedOffButton() {
		String curText = commitText.getText();
		if (!curText.endsWith(Text.DELIMITER))
			curText += Text.DELIMITER;

		signedOffItem.setSelection(curText.indexOf(getSignedOff() + Text.DELIMITER) != -1);
	}

	private void refreshSignedOffBy() {
		String curText = commitText.getText();
		if (signedOffItem.getSelection()) {
			commitText.setText(signOff(curText));
		} else {
			String s = getSignedOff();
			if (s != null) {
				curText = replaceSignOff(curText, s, ""); //$NON-NLS-1$
				if (curText.endsWith(Text.DELIMITER + Text.DELIMITER))
					curText = curText.substring(0, curText.length()
							- Text.DELIMITER.length());
				commitText.setText(curText);
			}
		}
	}

	private String replaceSignOff(String input, String oldSignOff, String newSignOff) {
		assert input != null;
		assert oldSignOff != null;
		assert newSignOff != null;

		String curText = input;
		if (!curText.endsWith(Text.DELIMITER))
			curText += Text.DELIMITER;

		int indexOfSignOff = curText.indexOf(oldSignOff + Text.DELIMITER);
		if (indexOfSignOff == -1)
			return input;

		return input.substring(0, indexOfSignOff) + newSignOff + input.substring(indexOfSignOff + oldSignOff.length(), input.length());
