	public String toString(int limit) {
		try {
			return RawParseUtils.decode(toByteArray(limit));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

