
	private static String longString(int len) {
		StringBuilder r = new StringBuilder(len);
		r.append('t');
		for (int i = 0; i < len - 1; i++)
			r.append('a');
		return r.toString();
	}
