		Object oldValue = event.diff.getOldValue();
		Object newValue = event.diff.getNewValue();
		ListDiffEntry[] diffEntries = new ListDiffEntry[2 * detailIndexes
				.cardinality()];
		int diffIndex = 0;
		for (int b = detailIndexes.nextSetBit(0); b != -1; b = detailIndexes
				.nextSetBit(b + 1)) {
			diffEntries[diffIndex++] = Diffs.createListDiffEntry(b, false,
					oldValue);
			diffEntries[diffIndex++] = Diffs.createListDiffEntry(b, true,
					newValue);
