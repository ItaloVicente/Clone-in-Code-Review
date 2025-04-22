	private static byte parseHexByte(byte c1
		try {
			return (byte) ((RawParseUtils.parseHexInt4(c1) << 4)
					| RawParseUtils.parseHexInt4(c2));
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new URISyntaxException(s
		}
	}

