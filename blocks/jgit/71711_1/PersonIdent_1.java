	private static void appendSanitized(StringBuilder r
		int i = 0;
		while (i < str.length() && str.charAt(i) <= ' ') {
			i++;
		}
		int end = str.length();
		while (end > i && str.charAt(end - 1) <= ' ') {
			end--;
		}

		for (; i < end; i++) {
			char c = str.charAt(i);
			switch (c) {
				case '\n':
				case '<':
				case '>':
					continue;
				default:
					r.append(c);
					break;
			}
		}
	}

