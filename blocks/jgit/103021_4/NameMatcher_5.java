	public boolean matches(String path
			boolean pathMatch) {
		int start = 0;
		int stop = path.length();
		if (stop > 0 && path.charAt(0) == slash) {
			start++;
		}
		if (pathMatch) {
			int lastSlash = path.lastIndexOf(slash
			if (lastSlash == stop - 1) {
				lastSlash = path.lastIndexOf(slash
				stop--;
			}
			boolean match;
			if (lastSlash < start) {
				match = matches(path
			} else {
				match = !beginning
						&& matches(path
			}
			if (match && dirOnly) {
				match = dirOnly == assumeDirectory;
			}
			return match;
		}
		while (start < stop) {
			int end = path.indexOf(slash
			if (end < 0) {
				end = stop;
			}
			if (end > start && matches(path
