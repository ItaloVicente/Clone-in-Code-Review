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
		if (nestedList.isStale()) {
			stale = null;
		}

		if (hasListeners()) {
			nestedList.removeListChangeListener(nestedListChangeListener);
		}

		cachedSizes.remove(listIx);
	}

	private void topListChanged(ListChangeEvent<? extends IObservableList<E>> event) {
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

