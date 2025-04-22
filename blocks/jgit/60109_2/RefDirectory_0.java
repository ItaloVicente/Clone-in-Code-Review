		try {
			RefList<Ref> packed = getPackedRefs();
			for (String prefix : SEARCH_PATH) {
				Ref ref = readAndResolve(prefix + needle
				if (ref != null) {
					return ref;
				}
