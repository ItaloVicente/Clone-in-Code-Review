	private static int nextStart(byte[] prefix
		int stop = buffer.length - prefix.length + 1;
		int ptr = from;
		if (ptr > 0) {
			ptr = RawParseUtils.nextLF(buffer
		}
		while (ptr < stop) {
			int lineStart = ptr;
			boolean found = true;
			for (byte element : prefix) {
				if (element != buffer[ptr++]) {
					found = false;
					break;
				}
			}
			if (found) {
				return lineStart;
			}
			do {
				ptr = RawParseUtils.nextLF(buffer
			} while (ptr < stop && buffer[ptr] == '\n');
		}
		return -1;
	}

	public final byte[] getRawGpgSignature() {
		byte[] raw = buffer;
		int msgB = RawParseUtils.tagMessage(raw
		if (msgB < 0) {
			return null;
		}
		int start = nextStart(hSignature
		if (start < 0) {
			return null;
		}
		int next = RawParseUtils.nextLF(raw
		while (next < raw.length) {
			int newStart = nextStart(hSignature
			if (newStart < 0) {
				break;
			}
			start = newStart;
			next = RawParseUtils.nextLF(raw
		}
		return Arrays.copyOfRange(raw
	}

