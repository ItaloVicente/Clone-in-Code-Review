		final boolean startsWith = path.startsWith("/");
		final boolean endsWith = path.endsWith("/");
		if (startsWith && endsWith) {
			return path.substring(1
		}
		if (startsWith) {
			return path.substring(1);
		}
		if (endsWith) {
			return path.substring(0
		}
		return path;
	}
