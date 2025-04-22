	private String unescape(String s) throws URISyntaxException {
		if (s == null)
			return null;
		if (getScheme() == null)
			return s;
		if (s.indexOf('%') < 0)
			return s;
		byte[] bytes;
		try {
			bytes = s.getBytes(Constants.CHARACTER_ENCODING);
		} catch (UnsupportedEncodingException e) {
		}

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		for (int i = 0; i < bytes.length; ++i) {
			byte c = bytes[i];
			if (c == '%') {
				if (i + 1 > bytes.length)
					throw new URISyntaxException(s
				int ascValue = Integer.parseInt(s.substring(i+1
				os.write(ascValue);
				i += 2;
			} else
				os.write(c);
		}
		return RawParseUtils.decode(os.toByteArray());
	}

	static BitSet reservedChars = new BitSet(127);

	static {
		for (byte b : "!*'();:@&=+$
			reservedChars.set(b);
		}
	}

	private String escape(String s
		if (s == null)
			return null;
		if (getScheme() == null)
			return s;
		StringBuilder os = new StringBuilder();
		byte[] bytes;
		try {
			bytes = s.getBytes(Constants.CHARACTER_ENCODING);
		} catch (UnsupportedEncodingException e) {
		}
		for (int i = 0; i < bytes.length; ++i) {
			int b = bytes[i] & 0xFF;
			if (b <= 32 || b > 127 || b == '%'
					|| (reserve && reservedChars.get(b))) {
				os.append('%');
				os.append(Integer.toHexString(b));
			} else {
				os.append((char) b);
			}
		}
		return os.toString();
	}

