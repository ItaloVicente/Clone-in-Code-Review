
		if (len == 0) {
			return 0;
		} else if (len < 4) {
			throw invalidHeader();
		}

		if (limit != 0) {
			int n = len - 4;
			if (limit < n) {
				limit = -1;
				try {
					IO.skipFully(in
				} catch (IOException e) {
				}
				throw new InputOverLimitIOException();
			}
			limit = n < limit ? limit - n : -1;
		}
		return len;
	}

	private IOException invalidHeader() {
		return new IOException(MessageFormat.format(JGitText.get().invalidPacketLineHeader
				+ (char) lineBuffer[2] + (char) lineBuffer[3]));
	}

	public static class InputOverLimitIOException extends IOException {
		private static final long serialVersionUID = 1L;
