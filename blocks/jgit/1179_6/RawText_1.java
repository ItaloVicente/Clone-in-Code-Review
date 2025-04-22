		return isBinary(raw
	}

	public static boolean isBinary(InputStream raw) throws IOException {
		final byte[] buffer = new byte[FIRST_FEW_BYTES];
		int cnt = 0;
		while (cnt < buffer.length) {
			final int n = raw.read(buffer
			if (n == -1)
				break;
			cnt += n;
		}
		return isBinary(buffer
	}

	public static boolean isBinary(byte[] raw
