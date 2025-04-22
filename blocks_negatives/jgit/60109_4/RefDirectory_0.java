		final RefList<Ref> packed = getPackedRefs();
		Ref ref = null;
		for (String prefix : SEARCH_PATH) {
			ref = readAndResolve(prefix + needle, packed);
			if (ref != null) {
				break;
