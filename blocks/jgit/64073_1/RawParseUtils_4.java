	@Nullable
	public static String parseEncodingName(final byte[] b) {
		int enc = encoding(b
		if (enc < 0) {
			return null;
		}
		int lf = nextLF(b
		return decode(UTF_8
	}

