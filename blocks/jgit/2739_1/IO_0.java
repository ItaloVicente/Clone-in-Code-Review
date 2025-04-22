	public static final byte[] readSome(final File path
			throws FileNotFoundException
		final FileInputStream in = new FileInputStream(path);
		try {
			ByteArrayOutputStream result = new ByteArrayOutputStream(limit);
			final byte[] buf = new byte[1024];
			int read = in.read(buf);
			while (read > 0) {
				result.write(buf
				read = in.read(buf);
			}

			return result.toByteArray();
		} finally {
			try {
				in.close();
			} catch (IOException ignored) {
			}
		}
	}

