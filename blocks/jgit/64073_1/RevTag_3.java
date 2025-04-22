	private Charset guessEncoding() {
		try {
			return RawParseUtils.parseEncoding(buffer);
		} catch (IllegalCharsetNameException | UnsupportedCharsetException e) {
			return UTF_8;
		}
	}

