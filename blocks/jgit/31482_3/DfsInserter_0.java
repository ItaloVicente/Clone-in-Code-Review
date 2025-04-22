	private byte[] insertBuffer(long len) {
		byte[] buf = buffer();
		if (len <= buf.length)
			return buf;
		if (len < db.getReaderOptions().getStreamFileThreshold()) {
			try {
				return new byte[(int) len];
			} catch (OutOfMemoryError noMem) {
				return buf;
			}
		}
		return buf;
	}

