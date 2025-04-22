	private Charset guessEncoding() {
		try {
			return getEncoding();
		} catch (IllegalCharsetNameException | UnsupportedCharsetException e) {
			return UTF_8;
		}
	}

