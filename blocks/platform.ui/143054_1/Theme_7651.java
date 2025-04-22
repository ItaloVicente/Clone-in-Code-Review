		String string = getString(key);
		if (string == null) {
			return 0;
		}
		try {
			return Integer.parseInt(string);
		} catch (NumberFormatException e) {
