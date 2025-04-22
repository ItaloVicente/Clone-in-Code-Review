	private InputStream possiblyFilteredInputStream(final Entry e
			final InputStream is
		InputStream filteredIs;
		if (!mightNeedCleaning()) {
			filteredIs = is;
			canonLen = len;
		} else {
			if (len <= MAXIMUM_FILE_SIZE_TO_READ_FULLY) {
				ByteBuffer rawbuf = IO.readWholeStream(is
				byte[] raw = rawbuf.array();
				int n = rawbuf.limit();
				if (!isBinary(raw
					rawbuf = filterClean(raw
					raw = rawbuf.array();
					n = rawbuf.limit();
				}
				filteredIs = new ByteArrayInputStream(raw
				canonLen = n;
			} else {
				if (isBinary(e)) {
					filteredIs = is;
					canonLen = len;
				} else {
					final InputStream lenIs = filterClean(e
							.openInputStream());
					try {
						canonLen = computeLength(lenIs);
					} finally {
						safeClose(lenIs);
					}
					filteredIs = filterClean(is);
				}
			}
		}
		return filteredIs;
	}

