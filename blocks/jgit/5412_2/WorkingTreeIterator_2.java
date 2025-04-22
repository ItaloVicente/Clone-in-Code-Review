	public long getEntryContentLength() throws IOException {
		if (canonLen == -1) {
			long rawLen = getEntryLength();
			if (rawLen == 0)
				canonLen = 0;
			InputStream is = current().openInputStream();
			try {
				possiblyFilteredInputStream(current()
						.getLength());
			} finally {
				safeClose(is);
			}
		}
		return canonLen;
	}

