	/** {@inheritDoc} */
	@Override
	public Ref getRef(String name) throws IOException {
		String[] needle = new String[SEARCH_PATH.length];
		for (int i = 0; i < SEARCH_PATH.length; i++) {
			needle[i] = SEARCH_PATH[i] + name;
		}
		return firstExactRef(needle);
	}

