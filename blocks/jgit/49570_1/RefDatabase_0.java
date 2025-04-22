	public Ref findRef(String name) throws IOException {
		String[] names = SEARCH_PATH.clone();
		for (int i = 0; i < names.length; i++) {
			names[i] += name;
		}
		return firstExactRef(names);
	}
