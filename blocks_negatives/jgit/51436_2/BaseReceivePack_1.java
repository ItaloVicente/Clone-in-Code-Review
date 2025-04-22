	static String chomp(String line) {
		if (line != null && !line.isEmpty()
				&& line.charAt(line.length() - 1) == '\n') {
			return line.substring(0, line.length() - 1);
		}
		return line;
	}

