		Object[] elements = c.toArray();
		ListDiffEntry[] entries = new ListDiffEntry[elements.length];
		for (int i = 0; i < elements.length; i++) {
			wrappedList.add(index + i, elements[i]);
			entries[i] = Diffs
					.createListDiffEntry(index + i, true, elements[i]);
