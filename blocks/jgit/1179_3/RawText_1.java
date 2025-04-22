		final int size = raw.length > FIRST_FEW_BYTES ? FIRST_FEW_BYTES
				: raw.length;
		return isBinary(raw
	}

	public static boolean isBinary(InputStream raw) throws IOException {
		final byte[] buffer = new byte[FIRST_FEW_BYTES];
		int cnt = 0;
		while (cnt < buffer.length) {
			final int read = raw.read(buffer
			if (read == -1)
				break;

			cnt += read;
		}

		return isBinary(buffer
	}

	private static boolean isBinary(byte[] raw
		for (int ptr = 0; ptr < length; ptr++)
