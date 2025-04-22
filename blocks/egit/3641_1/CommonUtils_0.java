	private static LinkedList<String> splitIntoDigitAndNonDigitParts(String input) {
		LinkedList<String> parts = new LinkedList<String>();
		int partStart = 0;
		boolean previousWasDigit = Character.isDigit(input.charAt(0));
		for (int i = 1; i < input.length(); i++) {
			boolean isDigit = Character.isDigit(input.charAt(i));
			if (isDigit != previousWasDigit) {
				parts.add(input.substring(partStart, i));
				partStart = i;
				previousWasDigit = isDigit;
			}
		}
		parts.add(input.substring(partStart));
		return parts;
	}

	private static String stripLeadingZeros(String input) {
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) != '0') {
				return input.substring(i);
			}
		}
		return ""; //$NON-NLS-1$
	}
