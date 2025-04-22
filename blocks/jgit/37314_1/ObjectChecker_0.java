	public void checkPath(String path) throws CorruptObjectException {
		byte[] buf = Constants.encode(path);
		checkPath(buf
	}

	public void checkPath(byte[] raw
			throws CorruptObjectException {
		int start = ptr;
		for (; ptr < end; ptr++) {
			if (raw[ptr] == '/') {
				checkPathSegment(raw
				start = ptr + 1;
			}
		}
		checkPathSegment(raw
	}

