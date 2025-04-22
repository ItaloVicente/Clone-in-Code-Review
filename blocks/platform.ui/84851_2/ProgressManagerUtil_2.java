
		s = clipToLength(textValue, ellipsisString, pivot, currentLength);
		return s;
	}

	private static String clipToLength(String textValue, String ellipsisString, int pivot, int newLength) {
		return getClippedString(textValue, ellipsisString, pivot, textValue.length() - newLength);
	}

	private static String getClippedString(String textValue, String ellipsisString, int pivot, int charsToClip) {
		int length = textValue.length();
		if (charsToClip <= 0) {
			return textValue;
		}
		if (charsToClip >= length) {
			return ""; //$NON-NLS-1$
		}
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
		s = s1 + ellipsisString + s2;
		return s;
