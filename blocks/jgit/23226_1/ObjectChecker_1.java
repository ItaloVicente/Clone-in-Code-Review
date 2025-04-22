	private boolean isDotGit(byte[] buf
		if (ignoreCase)
			return toLower(buf[p]) == 'g'
					&& toLower(buf[p + 1]) == 'i'
					&& toLower(buf[p + 2]) == 't';
		return buf[p] == 'g' && buf[p + 1] == 'i' && buf[p + 2] == 't';
	}

	private static byte toLower(byte b) {
		if ('A' <= b && b <= 'Z')
			return (byte) (b + ('a' - 'A'));
		return b;
	}

