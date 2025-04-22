	private void addNestedList(IObservableList<E> nestedList, List<ListDiffEntry<E>> diffEntries) {
		int offset = computeOffset(nestedList);
		for (E e : nestedList) {
			diffEntries.add(Diffs.createListDiffEntry(offset, true, e));
			offset++;
		}
		nestedList.addListChangeListener(nestedListChangeListener);
	}

	private void replaceNestedList(int listIx, IObservableList<E> oldNestedList, IObservableList<E> newNestedList,
			List<ListDiffEntry<E>> diffEntries) {
		int offset = computeCachedOffset(listIx);
		int ix = 0;

		for (; ix < oldNestedList.size() && ix < newNestedList.size(); ix++) {
			diffEntries.add(Diffs.createListDiffEntry(offset, false, oldNestedList.get(ix)));
			diffEntries.add(Diffs.createListDiffEntry(offset, true, newNestedList.get(ix)));
		}

		if (oldNestedList.size() < newNestedList.size()) {
			for (; ix < newNestedList.size(); ix++) {
				diffEntries.add(Diffs.createListDiffEntry(offset + ix, true, newNestedList.get(ix)));
			}
		} else if (oldNestedList.size() > newNestedList.size()) {
			for (; ix < oldNestedList.size(); ix++) {
				diffEntries.add(Diffs.createListDiffEntry(offset + ix, false, oldNestedList.get(ix)));
			}
		}
		oldNestedList.removeListChangeListener(nestedListChangeListener);
		newNestedList.addListChangeListener(nestedListChangeListener);
	}

