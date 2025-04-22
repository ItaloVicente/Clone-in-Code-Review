	/** {@inheritDoc} */
	@Override
	public Ref getRef(String needle) throws IOException {
		RefCache curr = read();
		for (String prefix : SEARCH_PATH) {
			Ref ref = curr.ids.get(prefix + needle);
			if (ref != null) {
				ref = resolve(ref, 0, curr.ids);
				return ref;
			}
		}
		return null;
	}

