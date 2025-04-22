
	public static String replaceLineBreaksWithSpace(String in) {
		char[] buf = new char[in.length()];
		int o = 0;
		for (int i = 0; i < buf.length; ++i) {
			char ch = in.charAt(i);
			if (ch == '\r') {
				if (i + 1 < buf.length && in.charAt(i + 1) == '\n') {
					buf[o++] = ' ';
					++i;
				} else
					buf[o++] = ' ';
			} else if (ch == '\n')
				buf[o++] = ' ';
			else
				buf[o++] = ch;
		}
		return new String(buf
	}
