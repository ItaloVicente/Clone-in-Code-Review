	public ByteBuffer getBytes() {
		
		return null;
	}

	protected short decodeShort(byte[] data, int i) {
		return (short)((data[i] & 0xff) << 8 | (data[i + 1] & 0xff));
	}

	protected int decodeInt(byte[] data, int i) {
		return (data[i] & 0xff) << 24
			| (data[i + 1] & 0xff) << 16
			| (data[i + 2] & 0xff) << 8
			| (data[i + 3] & 0xff);
		  }

	protected long decodeLong(byte[] data, int i) {
		return (data[i] & 0xffL) << 56
			| (data[i + 1] & 0xffL) << 48
			| (data[i + 2] & 0xffL) << 40
			| (data[i + 3] & 0xffL) << 32
			| (data[i + 4] & 0xffL) << 24
			| (data[i + 5] & 0xffL) << 16
			| (data[i + 6] & 0xffL) << 8
			| (data[i + 7] & 0xffL);
