	public final Ref findRef(String name) throws IOException {
		String[] names = new String[SEARCH_PATH.length];
		for (int i = 0; i < SEARCH_PATH.length; i++) {
			names[i] = SEARCH_PATH[i] + name;
		}
		return firstExactRef(names);
	}
