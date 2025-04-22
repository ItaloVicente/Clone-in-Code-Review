	private static boolean validCapabilityChar(int c) {
		String validChars = "* -_.
		return Character.isAlphabetic(c) || Character.isDigit(c) || validChars.indexOf(c) != -1;
	}

	private static boolean validCapability(String cap) {
		return cap.chars().allMatch(FirstWant::validCapabilityChar);
	}

