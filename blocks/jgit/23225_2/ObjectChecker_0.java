	private static void checkPathSegment(byte[] raw
			throws CorruptObjectException {
		if (ptr == end)
			throw new CorruptObjectException("zero length name");
		if (raw[ptr] == '.') {
			int nameLen = end - ptr;
			if (nameLen == 1)
				throw new CorruptObjectException("invalid name '.'");
			if (nameLen == 2 && raw[ptr + 1] == '.')
				throw new CorruptObjectException("invalid name '..'");
		}
	}

