	/**
	 * Check if path is a valid path for a checked out file name or ref name.
	 *
	 * @param path
	 * @throws InvalidPathException
	 *             if the path is invalid
	 * @since 3.3
	 */
	static void checkValidPath(String path) throws InvalidPathException {
		try {
			SystemReader.getInstance().checkPath(path);
		} catch (CorruptObjectException e) {
			InvalidPathException p = new InvalidPathException(path);
			p.initCause(e);
			throw p;
		}
	}

