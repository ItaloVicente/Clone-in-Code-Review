		List<ListDiffEntry<M>> masterEntries = masterListDiff
				.getDifferencesAsList();
		List<ListDiffEntry<E>> detailEntries = new ArrayList<ListDiffEntry<E>>(
				masterEntries.size());
		for (int i = 0; i < masterEntries.size(); i++) {
			ListDiffEntry<M> masterEntry = masterEntries.get(i);
