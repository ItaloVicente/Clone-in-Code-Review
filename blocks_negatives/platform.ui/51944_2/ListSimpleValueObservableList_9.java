	private void fireListChange(int[] indices, Object oldValue, Object newValue) {
		ListDiffEntry[] differences = new ListDiffEntry[indices.length * 2];
		for (int i = 0; i < indices.length; i++) {
			int index = indices[i];
			differences[i * 2] = Diffs.createListDiffEntry(index, false,
					oldValue);
			differences[i * 2 + 1] = Diffs.createListDiffEntry(index, true,
					newValue);
