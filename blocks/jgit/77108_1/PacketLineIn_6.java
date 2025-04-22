		return len;
	}

	private IOException invalidHeader() {
		return new IOException(MessageFormat.format(JGitText.get().invalidPacketLineHeader
				+ (char) lineBuffer[2] + (char) lineBuffer[3]));
	}

	public static class InputOverLimitIOException extends IOException {
		private static final long serialVersionUID = 1L;
