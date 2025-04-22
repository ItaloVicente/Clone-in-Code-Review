	public boolean matches(String path
			boolean pathMatch) {
		if (dirOnly && pathMatch) {
			return false;
		}
		if (matchers == null) {
			return simpleMatch(path
		}
		return iterate(path
