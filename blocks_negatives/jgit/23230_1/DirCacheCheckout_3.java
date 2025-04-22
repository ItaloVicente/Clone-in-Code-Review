
	private static byte toUpper(byte b) {
		if (b >= 'a' && b <= 'z')
			return (byte) (b - ('a' - 'A'));
		return b;
	}

