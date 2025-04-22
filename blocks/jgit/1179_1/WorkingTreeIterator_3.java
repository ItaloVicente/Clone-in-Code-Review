	private long determineCanonicalizedLength(InputStream raw) throws IOException {
		long length = 0;

		final EolCanonicalizingInputStream is = new EolCanonicalizingInputStream(raw);
		for (int read = 0; read != -1; read = is.read(contentReadBuffer)) {
			length += read;
		}

		return length;
	}

