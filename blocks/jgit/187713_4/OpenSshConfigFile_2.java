		}
		return result;
	}

	private static int parseToken(String argument
			List<String> result) {
		StringBuilder b = new StringBuilder();
		int i = from;
		char quote = 0;
		boolean escaped = false;
		SCAN: while (i < to) {
			char ch = argument.charAt(i);
			switch (ch) {
			case '"':
			case '\'':
				if (quote == 0) {
					if (escaped) {
						b.append(ch);
					} else {
						quote = ch;
					}
				} else if (!escaped && quote == ch) {
					quote = 0;
				} else {
					b.append(ch);
