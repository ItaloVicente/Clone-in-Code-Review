	private static final int objectHeader(long len
		byte b = (byte) ((type << 4) | (len & 0x0F));
		int n = 0;
		for (len >>>= 4; len != 0; len >>>= 7) {
			buf[n++] = (byte) (0x80 | b);
			b = (byte) (len & 0x7F);
