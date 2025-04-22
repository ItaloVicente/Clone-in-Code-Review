		int start = pivot - (charsToClip / 2);
		int end = pivot + (charsToClip / 2) + 1;
		while (start >= 0 && end < length) {
			String s1 = textValue.substring(0, start);
			String s2 = textValue.substring(end, length);
			String s = s1 + ellipsis + s2;
