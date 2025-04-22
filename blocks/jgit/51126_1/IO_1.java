	public static String readLine(Reader in
		StringBuilder buf = sizeHint > 0
				? new StringBuilder(sizeHint)
				: new StringBuilder();
		int i;
		while ((i = in.read()) != -1) {
			char c = (char) i;
			buf.append(c);
			if (c == '\n') {
				break;
			}
		}
		return buf.toString();
	}

