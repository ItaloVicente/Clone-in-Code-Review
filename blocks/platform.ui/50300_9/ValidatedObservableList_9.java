		List<ListDiffEntry<E>> entries = new ArrayList<ListDiffEntry<E>>(
				c.size());
		int i = index;
		for (E element : c) {
			wrappedList.add(i, element);
			entries.add(Diffs.createListDiffEntry(i, true, element));
			i++;
