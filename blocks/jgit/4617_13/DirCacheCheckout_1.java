
	private static byte[][] forbidden;
	static {
		String[] list = new String[] { "AUX"
				"COM5"
				"LPT3"
				"PRN" };
		forbidden = new byte[list.length][];
		for (int i = 0; i < list.length; ++i)
			forbidden[i] = Constants.encodeASCII(list[i]);
	}

	private static boolean isValidPath(CanonicalTreeParser t) {
		for (CanonicalTreeParser i = t; i != null; i = i.getParent())
			if (!isValidPathSegment(i))
				return false;
		return true;
	}

	private static boolean isValidPathSegment(CanonicalTreeParser t) {
		boolean isWindows = SystemReader.getInstance().getProperty("os.name")
				.equals("Windows");
		boolean isOSX = SystemReader.getInstance().getProperty("os.name")
				.equals("Mac OS X");
		boolean ignCase = isOSX || isWindows;

		int ptr = t.pathSegmentOffset();
		byte[] raw = t.pathSegmentBuffer();
		int end = ptr + t.pathSegmentLength();

		int start = ptr;
		while (raw[ptr] != 0 && ptr < end) {
			if (raw[ptr] == '/')
				return false;
			if (isWindows) {
				if (raw[ptr] == '\\')
					return false;
				if (raw[ptr] == ':')
					return false;
			}
			ptr++;
		}
		if (ptr - start == 1) {
			if (raw[start] == '.')
				return false;
		} else if (ptr - start == 2) {
			if (raw[start] == '.')
				if (raw[start + 1] == '.')
					return false;
		} else if (ptr - start == 4) {
			if (raw[start] == '.')
				if (raw[start + 1] == 'g' || (ignCase && raw[start + 1] == 'G'))
					if (raw[start + 2] == 'i'
							|| (ignCase && raw[start + 2] == 'I'))
						if (raw[start + 3] == 't'
								|| (ignCase && raw[start + 3] == 'T'))
							return false;
		}
		if (isWindows) {
			if (raw[ptr - 1] == '.' || raw[ptr - 1] == ' ')
				return false;
			int i;
			for (i = start; i < ptr; ++i)
				if (raw[i] == '.')
					break;
			int len = i - start;
			if (len == 3 || len == 4) {
				for (int j = 0; j < forbidden.length; ++j) {
					if (forbidden[j].length == len) {
						if (toUpper(raw[start]) < forbidden[j][0])
							break;
						int k;
						for (k = 0; k < len; ++k) {
							if (toUpper(raw[start + k]) != forbidden[j][k])
								break;
						}
						if (k == len)
							return false;
					}
				}
			}
		}

		return true;
	}

	private static byte toUpper(byte b) {
		if (b >= 'a' && b <= 'z')
			return (byte) (b - ('a' - 'A'));
		return b;
	}

