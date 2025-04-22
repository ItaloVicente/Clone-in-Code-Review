	private static String unescape(String s) throws URISyntaxException {
		if (s == null)
			return null;
		if (s.indexOf('%') < 0)
			return s;

		byte[] bytes;
		try {
			bytes = s.getBytes(Constants.CHARACTER_ENCODING);
		} catch (UnsupportedEncodingException e) {
		}

		byte[] os = new byte[bytes.length];
		int j = 0;
		for (int i = 0; i < bytes.length; ++i) {
			byte c = bytes[i];
			if (c == '%') {
				if (i + 2 > bytes.length)
					throw new URISyntaxException(s
				int val = (RawParseUtils.parseHexInt4(bytes[i + 1]) << 4)
						| RawParseUtils.parseHexInt4(bytes[i + 2]);
				os[j++] = (byte) val;
				i += 2;
			} else
				os[j++] = c;
		}
		return RawParseUtils.decode(os
	}

	private static final BitSet reservedChars = new BitSet(127);

	static {
		for (byte b : Constants.encodeASCII("!*'();:@&=+$
			reservedChars.set(b);
	}

	private static String escape(String s
			boolean encodeNonAscii) {
		if (s == null)
			return null;
		ByteArrayOutputStream os = new ByteArrayOutputStream(s.length());
		byte[] bytes;
		try {
			bytes = s.getBytes(Constants.CHARACTER_ENCODING);
		} catch (UnsupportedEncodingException e) {
		}
		for (int i = 0; i < bytes.length; ++i) {
			int b = bytes[i] & 0xFF;
			if (b <= 32 || (encodeNonAscii && b > 127) || b == '%'
					|| (escapeReservedChars && reservedChars.get(b))) {
				os.write('%');
				byte[] tmp = Constants.encodeASCII(String.format("%02x"
						Integer.valueOf(b)));
				os.write(tmp[0]);
				os.write(tmp[1]);
			} else {
				os.write(b);
			}
		}
		byte[] buf = os.toByteArray();
		return RawParseUtils.decode(buf
	}

