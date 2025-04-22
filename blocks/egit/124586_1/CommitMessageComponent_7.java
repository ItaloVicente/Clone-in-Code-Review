	private boolean hasSignOff(String input, String signature) {
		String curText = input;
		if (!curText.endsWith(Text.DELIMITER)) {
			curText += Text.DELIMITER;
		}
		return curText.indexOf(signature + Text.DELIMITER) >= 0;
	}

