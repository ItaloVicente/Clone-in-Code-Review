	private static String toCGitFormat(String path) {
		if (!path.contains("\\")) {
			return path;
		}
		final StringBuilder pathBuilder = new StringBuilder("\"");
		for (int i = 0; i < path.length(); i++) {
			final char c = path.charAt(i);
			if (c == '\\') {
				pathBuilder.append('\\');
			}
			pathBuilder.append(c);
		}
		pathBuilder.append('"');
		return pathBuilder.toString();
	}

