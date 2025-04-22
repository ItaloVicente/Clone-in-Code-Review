	private static void checkPath(byte[] path) {
		try {
			SystemReader.getInstance().checkPath(path);
		} catch (CorruptObjectException e) {
			InvalidPathException p = new InvalidPathException(toString(path));
			p.initCause(e);
			throw p;
		}
	}

