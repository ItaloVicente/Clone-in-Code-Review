	private void fireListChange(int[] indices, T oldValue, T newValue) {
		List<ListDiffEntry<T>> differences = new ArrayList<ListDiffEntry<T>>(
				indices.length * 2);
		for (int index : indices) {
			differences.add(Diffs.createListDiffEntry(index, false, oldValue));
			differences.add(Diffs.createListDiffEntry(index, true, newValue));
