public class Base64 {
	private final static byte EQUALS_SIGN = (byte) '=';

	private final static byte EQUALS_SIGN_DEC = -1;

	private final static byte WHITE_SPACE_DEC = -2;

	private final static byte INVALID_DEC = -3;

	private final static String UTF_8 = "UTF-8";

	private final static byte[] ENC;

	private final static byte[] DEC;

	static {
		try {
			).getBytes(UTF_8);
		} catch (UnsupportedEncodingException uee) {
			throw new RuntimeException(uee.getMessage()
		}

		DEC = new byte[128];
		Arrays.fill(DEC

		for (int i = 0; i < 64; i++)
			DEC[ENC[i]] = (byte) i;
		DEC[EQUALS_SIGN] = EQUALS_SIGN_DEC;

		DEC['\t'] = WHITE_SPACE_DEC;
		DEC['\n'] = WHITE_SPACE_DEC;
		DEC['\r'] = WHITE_SPACE_DEC;
		DEC[' '] = WHITE_SPACE_DEC;
	}

	private Base64() {
	}

	private static void encode3to4(byte[] source
			int numSigBytes

		int inBuff = 0;
		switch (numSigBytes) {
		case 3:
			inBuff |= (source[srcOffset + 2] << 24) >>> 24;

		case 2:
			inBuff |= (source[srcOffset + 1] << 24) >>> 16;

		case 1:
			inBuff |= (source[srcOffset] << 24) >>> 8;
		}

		switch (numSigBytes) {
		case 3:
			destination[destOffset] = ENC[(inBuff >>> 18)];
			destination[destOffset + 1] = ENC[(inBuff >>> 12) & 0x3f];
			destination[destOffset + 2] = ENC[(inBuff >>> 6) & 0x3f];
			destination[destOffset + 3] = ENC[(inBuff) & 0x3f];
			break;

		case 2:
			destination[destOffset] = ENC[(inBuff >>> 18)];
			destination[destOffset + 1] = ENC[(inBuff >>> 12) & 0x3f];
			destination[destOffset + 2] = ENC[(inBuff >>> 6) & 0x3f];
			destination[destOffset + 3] = EQUALS_SIGN;
			break;

		case 1:
			destination[destOffset] = ENC[(inBuff >>> 18)];
			destination[destOffset + 1] = ENC[(inBuff >>> 12) & 0x3f];
			destination[destOffset + 2] = EQUALS_SIGN;
			destination[destOffset + 3] = EQUALS_SIGN;
			break;
		}
	}

	public static String encodeBytes(byte[] source) {
