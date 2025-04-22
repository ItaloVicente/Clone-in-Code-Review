		}
		if (RawParseUtils
				.parsePersonIdent(committerText.getText().trim()) != null) {
			signedOff = curText.indexOf(getSignedOff() + Text.DELIMITER) != -1;
			listener.updateSignedOffToggleSelection(signedOff);
		}
