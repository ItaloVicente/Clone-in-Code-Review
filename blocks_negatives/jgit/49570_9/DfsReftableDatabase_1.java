	/** {@inheritDoc} */
	@Override
	public Ref getRef(String needle) throws IOException {
		for (String prefix : SEARCH_PATH) {
			Ref ref = exactRef(prefix + needle);
			if (ref != null) {
				return ref;
			}
		}
		return null;
	}

