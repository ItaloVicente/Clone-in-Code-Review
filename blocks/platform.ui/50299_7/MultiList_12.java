	private List<ListDiffEntry<E>> offsetListDiffEntries(int offset,
			List<ListDiffEntry<E>> entries) {
		List<ListDiffEntry<E>> offsetEntries = new ArrayList<ListDiffEntry<E>>(
				entries.size());
		for (ListDiffEntry<E> entry : entries) {
			offsetEntries.add(offsetListDiffEntry(offset, entry));
