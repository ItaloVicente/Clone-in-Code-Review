
	public static boolean isBinary(byte[] raw) {
		int size = raw.length > FIRST_FEW_BYTES ? FIRST_FEW_BYTES : raw.length;
		for (int ptr = 0; ptr < size; ptr++)
			if (raw[ptr] == '\0')
				return true;

		return false;
	}
