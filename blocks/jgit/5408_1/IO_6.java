	public static List<String> readLines(final String s) {
		List<String> l = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '\n') {
				l.add(sb.toString());
				sb.setLength(0);
				continue;
			}
			if (c == '\r') {
				if (i + 1 < s.length()) {
					c = s.charAt(++i);
					l.add(sb.toString());
					sb.setLength(0);
					if (c != '\n')
						sb.append(c);
					continue;
					l.add(sb.toString());
					break;
				}
			}
			sb.append(c);
		}
		l.add(sb.toString());
		return l;
	}

