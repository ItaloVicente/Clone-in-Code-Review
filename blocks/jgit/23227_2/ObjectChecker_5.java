	private static char toLower(byte b) {
		if ('A' <= b && b <= 'Z')
			return (char) (b + ('a' - 'A'));
		return (char) b;
	}

