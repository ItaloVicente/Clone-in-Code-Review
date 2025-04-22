	public static String read(final File file) throws IOException {
		final byte[] body = IO.readFully(file);
		return new String(body
	}

	public static String read(final FileRepository db
			throws IOException {
		File file = new File(db.getWorkTree()
		return read(file);
	}

