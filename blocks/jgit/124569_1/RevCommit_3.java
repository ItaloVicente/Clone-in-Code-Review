	public final byte[] getRawGpgSignature() {
		final byte[] raw = buffer;
		final byte[] header = {'g'
		final int start = RawParseUtils.headerStart(header
		if (start < 0)
			return null;
		final int end = RawParseUtils.nextHeader(raw
		return Arrays.copyOfRange(raw
	}

