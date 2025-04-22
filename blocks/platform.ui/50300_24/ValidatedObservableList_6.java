		List<ListDiffEntry<E>> entries = new ArrayList<>(c.size());
		int i = index;
		for (E element : c) {
			wrappedList.add(i, element);
			entries.add(Diffs.createListDiffEntry(i, true, element));
			i++;
