
	public static TapMagic getMagicByByte(byte b) {
		if (b == PROTOCOL_BINARY_REQ.magic) {
			return TapMagic.PROTOCOL_BINARY_REQ;
		} else if (b == PROTOCOL_BINARY_RES.magic) {
			return TapMagic.PROTOCOL_BINARY_RES;
		} else {
			throw new IllegalArgumentException("Bad magic value");
		}
	}
