		return s;
	}

	private static String getClippedString(String textValue, int pivot, int charsToClip) {
		int length = textValue.length();
		String s;
		int start = pivot - charsToClip / 2;
		int end = pivot + (charsToClip + 1) / 2;

		if (start < 0) {
			end -= start;
			start = 0;
		}
		if (end < 0) {
			start -= end;
			end = 0;
		}

		String s1 = textValue.substring(0, start);
		String s2 = textValue.substring(end, length);
		s = s1 + ellipsis + s2;
		return s;
