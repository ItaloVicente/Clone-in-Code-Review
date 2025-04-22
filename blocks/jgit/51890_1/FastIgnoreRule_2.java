		}
		dirOnly = pattern.charAt(pattern.length() - 1) == PATH_SEPARATOR;
		if (dirOnly) {
			pattern = stripTrailing(pattern
			if (pattern.length() == 0) {
				this.matcher = NO_MATCH;
				return;
