	public boolean matches(String path
			boolean pathMatch) {
		if (matchers == null) {
			return simpleMatch(path
		}
		return iterate(path
