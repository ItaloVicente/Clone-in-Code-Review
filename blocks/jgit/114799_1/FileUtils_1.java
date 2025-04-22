	public static File resolveSymLinks(final File f) throws IOException {
		try {
			if (!f.exists()) {
				return f;
			}

			return f.toPath().toRealPath().toFile();
		} catch (InvalidPathException ex) {
			throw new IOException(ex);
		}
	}

