	public static String asString(RGB value) {
		Assert.isNotNull(value);
		StringBuilder buffer = new StringBuilder();
		buffer.append(value.red);
		buffer.append(',');
		buffer.append(value.green);
		buffer.append(',');
		buffer.append(value.blue);
		return buffer.toString();
	}

	public static String asString(boolean value) {
		return String.valueOf(value);
	}

	public static String removeWhiteSpaces(String s) {
		boolean found = false;
		int wsIndex = -1;
		int size = s.length();
		for (int i = 0; i < size; i++) {
			found = Character.isWhitespace(s.charAt(i));
			if (found) {
				wsIndex = i;
				break;
			}
		}
		if (!found) {
