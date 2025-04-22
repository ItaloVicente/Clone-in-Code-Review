	public Ref getRef(String needle) throws IOException {
		final RefList<Ref> packed = getPackedRefs();
		Ref ref = null;
		for (String prefix : SEARCH_PATH) {
			try {
				ref = readRef(prefix + needle, packed);
