	public static final byte[] readSome(final File path
			throws FileNotFoundException
		final FileInputStream in = new FileInputStream(path);
		try {
			final byte[] buf = new byte[limit];
			int read = in.read(buf);
			if (read == buf.length) {
				return buf;
			}
			byte[] result = new byte[read];
			System.arraycopy(buf

			return result;
		} finally {
			try {
				in.close();
			} catch (IOException ignored) {
			}
		}
	}

