		return character;
	}

	private static int toUpperCase(int keyCode) {
		if (keyCode > 0xFFFF) {
			return keyCode;
		}

		char character = (char) keyCode;
		return Character.isLetter(character) ? Character.toUpperCase(character) : keyCode;
	}

	private SWTKeySupport() {
	}
