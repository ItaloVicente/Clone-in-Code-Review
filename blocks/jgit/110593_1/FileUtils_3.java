	public static Path toPath(final File f) throws IOException {
		try {
			return f.toPath();
		} catch (InvalidPathException ex) {
			throw new IOException(ex);
		}
	}

