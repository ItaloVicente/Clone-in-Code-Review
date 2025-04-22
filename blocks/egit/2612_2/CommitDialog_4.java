	private void updateSignedOffButton() {
		String curText = commitText.getText();
		if (!curText.endsWith(Text.DELIMITER))
			curText += Text.DELIMITER;

		signedOffButton.setSelection(curText.indexOf(getSignedOff() + Text.DELIMITER) != -1);
	}

	private void refreshSignedOffBy() {
		String curText = commitText.getText();
		if (signedOffButton.getSelection()) {
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

