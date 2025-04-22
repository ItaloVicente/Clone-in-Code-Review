	private int computeRealOffset(int listIx) {
		int ix = 0;
		int offset = 0;
		for (List<?> l : topLevelList) {
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
		if (nestedList.isStale()) {
			stale = null;
		}

		if (hasListeners()) {
			nestedList.removeListChangeListener(nestedListChangeListener);
		}

		cachedSizes.remove(listIx);
	}

	private void addNestedList(int listIx, IObservableList<E> nestedList, List<ListDiffEntry<E>> diffEntries) {
		int offset = computeRealOffset(listIx);
		for (E e : nestedList) {
			diffEntries.add(Diffs.createListDiffEntry(offset, true, e));
			offset++;
		}
		if (nestedList.isStale()) {
			stale = true;
		}

		if (hasListeners()) {
			nestedList.addStaleListener(staleListener);
			nestedList.addListChangeListener(nestedListChangeListener);
		}

		cachedSizes.add(listIx, nestedList.size());
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

		if (oldNestedList.isStale()) {
			if (!newNestedList.isStale()) {
				stale = null;
			}
		} else {
			if (newNestedList.isStale()) {
				stale = true;
			}
		}

		if (hasListeners()) {
			oldNestedList.removeListChangeListener(nestedListChangeListener);
			oldNestedList.removeStaleListener(staleListener);
			newNestedList.addListChangeListener(nestedListChangeListener);
			newNestedList.addStaleListener(staleListener);
		}

		cachedSizes.set(listIx, newNestedList.size());
	}

	private void moveNestedList(List<ListDiffEntry<E>> diffEntries, int oldListIx, int newListIx,
			IObservableList<E> nestedList) {
		int oldOffset = computeCachedOffset(oldListIx);
		int newOffset = computeRealOffset(newListIx);
		int ix = 0;
		for (E e : nestedList) {
			diffEntries.add(Diffs.createListDiffEntry(oldOffset + ix, false, e));
			diffEntries.add(Diffs.createListDiffEntry(newOffset + ix, true, e));
			ix++;
		}
		moveElement(cachedSizes, oldListIx, newListIx);
	}

	private static <E> void moveElement(List<E> list, int oldIx, int newIx) {
		E movedValue = list.get(oldIx);
		for (int ix = oldIx; ix < newIx - 1; ix++) {
			list.set(ix, list.get(ix + 1));
		}
		list.set(newIx, movedValue);
	}

	private void topLevelListChanged(ListChangeEvent<? extends IObservableList<E>> event) {
		List<ListDiffEntry<E>> diffEntries = new ArrayList<>();

		boolean wasStale = isStale();

		event.diff.accept(new ListDiffVisitor<IObservableList<E>>() {
			@Override
			public void handleAdd(int listIx, IObservableList<E> nestedList) {
				addNestedList(listIx, nestedList, diffEntries);
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
				moveNestedList(diffEntries, oldListIx, newListIx, nestedList);
			}
		});

		if (hasListeners()) {
			if (!wasStale && isStale()) {
				fireStale();
			}

			fireListChange(Diffs.createListDiff(diffEntries));
		}
	}

