	public static byte[] decode(String s) {
		byte[] bytes;
		try {
			bytes = s.getBytes(UTF_8);
		} catch (UnsupportedEncodingException uee) {
			bytes = s.getBytes();
		}
