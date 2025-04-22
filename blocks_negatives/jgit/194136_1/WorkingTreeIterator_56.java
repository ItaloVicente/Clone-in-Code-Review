			final InputStream is, final long len) throws IOException {
		return possiblyFilteredInputStream(e, is, len, null);

	}

	private InputStream possiblyFilteredInputStream(final Entry e,
			final InputStream is, final long len, OperationType opType)
