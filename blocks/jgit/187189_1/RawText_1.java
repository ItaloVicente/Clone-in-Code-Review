		int maxLength = getBufferSize();
		if (length > maxLength) {
			length = maxLength;
		}
		for (int ptr = 0; ptr < length; ptr++) {
			if (raw[ptr] == '\0') {
