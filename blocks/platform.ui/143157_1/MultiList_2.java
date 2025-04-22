	private int computeRealOffset(int listIx) {
		int ix = 0;
		int offset = 0;
		for (List<?> l : topList) {
			if (ix == listIx) {
				return offset;
			}
			offset += l.size();
			ix++;
		}
		throw new IllegalArgumentException("MultiList received a update for a invalid list index: " + listIx); //$NON-NLS-1$
	}

	private int computeCachedOffset(int listIx) {
		int offset = 0;
		for (int ix = 0; ix < cachedSizes.size(); ix++) {
			if (ix == listIx) {
				return offset;
			}
			offset += cachedSizes.get(ix);
		}
		throw new IllegalArgumentException("MultiList received a update for a invalid list index: " + listIx); //$NON-NLS-1$
	}

	private void removeNestedList(int listIx, IObservableList<E> nestedList, List<ListDiffEntry<E>> diffEntries) {
		int offset = computeCachedOffset(listIx);
		for (E e : nestedList) {
			diffEntries.add(Diffs.createListDiffEntry(offset, false, e));
			offset++;
		}
		nestedList.removeListChangeListener(nestedListChangeListener);
	}

	private void topListChanged(ListChangeEvent<? extends IObservableList<E>> event) {
		List<ListDiffEntry<E>> diffEntries = new ArrayList<>();

		event.diff.accept(new ListDiffVisitor<IObservableList<E>>() {
			@Override
			public void handleAdd(int listIx, IObservableList<E> nestedList) {
				addNestedList(nestedList, diffEntries);
			}

			@Override
			public void handleRemove(int listIx, IObservableList<E> nestedList) {
				removeNestedList(listIx, nestedList, diffEntries);
			}

			@Override
			public void handleReplace(int listIx, IObservableList<E> oldNestedList, IObservableList<E> newNestedList) {
				replaceNestedList(listIx, oldNestedList, newNestedList, diffEntries);
			}

			@Override
			public void handleMove(int oldListIx, int newListIx, IObservableList<E> nestedList) {
				int oldOffset = computeCachedOffset(oldListIx);
				int newOffset = computeRealOffset(newListIx);
				int ix = 0;
				for (E e : nestedList) {
					diffEntries.add(Diffs.createListDiffEntry(oldOffset + ix, false, e));
					diffEntries.add(Diffs.createListDiffEntry(newOffset + ix, true, e));
				}
			}
		});

		updateSizes();
		if (hasListeners()) {
			fireListChange(Diffs.createListDiff(diffEntries));
		}
	}

	private void updateSizes() {
		int ix = 0;
		for (List<?> l : topList) {
			cachedSizes.put(ix, l.size());
			ix++;
		}

		for (int i = cachedSizes.size() - 1; i > topList.size(); i--) {
			cachedSizes.remove(i);
		}
	}

