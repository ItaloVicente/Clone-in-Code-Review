				String curText = commitText.getText();
				if (signedOffButton.getSelection()) {
					commitText.setText(signOff(curText));
				} else {
					curText = replaceSignOff(curText, getSignedOff(), ""); //$NON-NLS-1$
					if (curText.endsWith(Text.DELIMITER + Text.DELIMITER))
						curText = curText.substring(0, curText.length() - Text.DELIMITER.length());
					commitText.setText(curText);
				}
