	private int scanPathSegment(byte[] raw
			throws CorruptObjectException {
		for (; ptr < end; ptr++) {
			byte c = raw[ptr];
			if (c == 0)
				return ptr;
			if (c == '/')
				throw new CorruptObjectException("name contains '/'");
			if (windows && isInvalidOnWindows(c)) {
				if (c > 31)
					throw new CorruptObjectException(String.format(
							"name contains '%c'"
				throw new CorruptObjectException(String.format(
						"name contains byte 0x%x"
			}
		}
		return ptr;
	}

	public void checkPathSegment(byte[] raw
			throws CorruptObjectException {
		int e = scanPathSegment(raw
		if (e < end && raw[e] == 0)
			throw new CorruptObjectException("name contains byte 0x00");
		checkPathSegment2(raw
	}

	private void checkPathSegment2(byte[] raw
