		E oldValue = event.diff.getOldValue();
		E newValue = event.diff.getNewValue();
		List<ListDiffEntry<E>> diffEntries = new ArrayList<>(2 * detailIndexes.cardinality());
		for (int b = detailIndexes.nextSetBit(0); b != -1; b = detailIndexes.nextSetBit(b + 1)) {
			diffEntries.add(Diffs.createListDiffEntry(b, false, oldValue));
			diffEntries.add(Diffs.createListDiffEntry(b, true, newValue));
