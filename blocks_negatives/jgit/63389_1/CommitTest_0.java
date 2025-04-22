	String toString(String[] arr) {
		StringBuilder sb = new StringBuilder();
		for (String s : arr) {
			if (s != null && !s.isEmpty()) {
				sb.append(s);
				if (!s.endsWith("\n")) {
					sb.append('\n');
				}
			}
		}
		return sb.toString();
	}
