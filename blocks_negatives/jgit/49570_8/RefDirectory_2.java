	/** {@inheritDoc} */
	@Override
	public Ref getRef(String needle) throws IOException {
		try {
			RefList<Ref> packed = getPackedRefs();
			for (String prefix : SEARCH_PATH) {
				Ref ref = readAndResolve(prefix + needle, packed);
				if (ref != null) {
					return ref;
				}
			}
			return null;
		} finally {
			fireRefsChanged();
		}
	}

