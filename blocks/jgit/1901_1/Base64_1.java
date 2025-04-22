	public static String encodeBytes(byte[] source
		final int len43 = len * 4 / 3;

		byte[] outBuff = new byte[len43 + ((len % 3) > 0 ? 4 : 0)];
		int d = 0;
		int e = 0;
		int len2 = len - 2;

		for (; d < len2; d += 3
			encode3to4(source

		if (d < len) {
			encode3to4(source
			e += 4;
		}

		try {
			return new String(outBuff
		} catch (UnsupportedEncodingException uue) {
			return new String(outBuff
		}
	}

	private static int decode4to3(byte[] source
			byte[] destination
		if (source[srcOffset + 2] == EQUALS_SIGN) {
			int outBuff = ((DEC[source[srcOffset]] & 0xFF) << 18)
					| ((DEC[source[srcOffset + 1]] & 0xFF) << 12);
			destination[destOffset] = (byte) (outBuff >>> 16);
			return 1;
		}

		else if (source[srcOffset + 3] == EQUALS_SIGN) {
			int outBuff = ((DEC[source[srcOffset]] & 0xFF) << 18)
					| ((DEC[source[srcOffset + 1]] & 0xFF) << 12)
					| ((DEC[source[srcOffset + 2]] & 0xFF) << 6);
			destination[destOffset] = (byte) (outBuff >>> 16);
			destination[destOffset + 1] = (byte) (outBuff >>> 8);
			return 2;
		}

		else {
			int outBuff = ((DEC[source[srcOffset]] & 0xFF) << 18)
					| ((DEC[source[srcOffset + 1]] & 0xFF) << 12)
					| ((DEC[source[srcOffset + 2]] & 0xFF) << 6)
					| ((DEC[source[srcOffset + 3]] & 0xFF));

			destination[destOffset] = (byte) (outBuff >> 16);
			destination[destOffset + 1] = (byte) (outBuff >> 8);
			destination[destOffset + 2] = (byte) (outBuff);

			return 3;
		}
	}

