	public static boolean isBinary(byte[] raw
		int maxLength = getBufferSize();
		if (length > maxLength) {
			length = maxLength;
		}
		for (int ptr = 0; ptr < length; ptr++) {
			byte curr = raw[ptr];
			if (isBinary(curr
				return true;
			}
			last = curr;
		}
		if (complete) {
		}
