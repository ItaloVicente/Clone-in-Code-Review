	private static boolean isMacHFSGit(byte[] raw
			throws CorruptObjectException {
		boolean ignorable = false;
		byte[] git = new byte[] { '.'
		int g = 0;
		while (ptr < end) {
			switch (raw[ptr]) {
				checkTruncatedIgnorableUTF8(raw
				switch (raw[ptr + 1]) {
				case (byte) 0x80:
					switch (raw[ptr + 2]) {
						ignorable = true;
						ptr += 3;
						continue;
					default:
						return false;
					}
				case (byte) 0x81:
					switch (raw[ptr + 2]) {
						ignorable = true;
						ptr += 3;
						continue;
					default:
						return false;
					}
				}
				break;
				checkTruncatedIgnorableUTF8(raw
				if ((raw[ptr + 1] == (byte) 0xbb)
						&& (raw[ptr + 2] == (byte) 0xbf)) {
					ignorable = true;
					ptr += 3;
					continue;
				}
				return false;
			default:
				if (g == 4)
					return false;
				if (raw[ptr++] != git[g++])
					return false;
			}
		}
		if (g == 4 && ignorable)
			return true;
		return false;
	}

	private static void checkTruncatedIgnorableUTF8(byte[] raw
			throws CorruptObjectException {
		if ((ptr + 2) >= end)
			throw new CorruptObjectException(MessageFormat.format(
				"invalid name contains byte sequence ''{0}'' which is not a valid UTF-8 character"
					toHexString(raw
	}

	private static String toHexString(byte[] raw
		for (int i = ptr; i < end; i++)
			b.append(String.format("%02x"
		return b.toString();
	}

