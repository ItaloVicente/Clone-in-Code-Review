	/**
	 * @param d
	 *            decoder to read key from current field from.
	 * @return the key
	 */
	public static CachedPackKey fromBytes(TinyProtobuf.Decoder d) {
		int len = d.bytesLength();
		int ptr = d.bytesOffset();
		byte[] buf = d.bytesArray();
		return fromBytes(buf, ptr, len);
	}

