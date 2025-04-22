	public boolean matches(String path
			boolean pathMatch) {
		if (dirOnly && pathMatch) {
			return false;
		}
		int start = 0;
		int stop = path.length();
		if (stop > 0 && path.charAt(0) == slash) {
			start++;
		}
		if (!dirOnly && pathMatch) {
			int lastSlash = path.lastIndexOf(slash
			if (lastSlash == stop - 1) {
				lastSlash = path.lastIndexOf(slash
				stop--;
			}
			if (lastSlash < start) {
				return matches(path
			}
			if (beginning) {
				return false;
			}
			return matches(path
		}
		while (start < stop) {
			int end = path.indexOf(slash
			if (end < 0) {
				end = stop;
			}
			if (end > start && matches(path
