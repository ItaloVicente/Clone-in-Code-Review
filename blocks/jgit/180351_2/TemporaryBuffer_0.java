	@Override
	public String toString() {
		try {
			return RawParseUtils.decode(toByteArray(MAX_TEXT_SIZE));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public String toString(int limit) {
		try {
			return RawParseUtils.decode(toByteArray(limit));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

