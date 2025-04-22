	public static boolean isCrLfText(byte[] raw) {
		return isCrLfText(raw
	}

	public static boolean isCrLfText(InputStream raw) throws IOException {
		byte[] buffer = new byte[FIRST_FEW_BYTES];
		int cnt = 0;
		while (cnt < buffer.length) {
			int n = raw.read(buffer
			if (n == -1) {
				break;
			}
			cnt += n;
		}
		return isCrLfText(buffer
	}

	public static boolean isCrLfText(byte[] raw
		boolean has_crlf = false;
		for (int ptr = 0; ptr < length - 1; ptr++) {
			if (raw[ptr] == '\0') {
			} else if (raw[ptr] == '\r' && raw[ptr + 1] == '\n') {
				has_crlf = true;
			}
		}
		return has_crlf;
	}

