	private final int encodeTypeSize(int type, long rawLength) {
		long nextLength = rawLength >>> 4;
		headerBuffer[0] = (byte) ((nextLength > 0 ? 0x80 : 0x00) | (type << 4) | (rawLength & 0x0F));
		rawLength = nextLength;
		int n = 1;
		while (rawLength > 0) {
			nextLength >>>= 7;
			headerBuffer[n++] = (byte) ((nextLength > 0 ? 0x80 : 0x00) | (rawLength & 0x7F));
			rawLength = nextLength;
