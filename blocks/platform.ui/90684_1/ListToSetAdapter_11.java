	private IListChangeListener<E> listener = event -> {
		Set<E> added = new HashSet<>();
		Set<E> removed = new HashSet<>();
		ListDiffEntry<? extends E>[] differences = event.diff.getDifferences();
		for (ListDiffEntry<? extends E> entry : differences) {
			E element = entry.getElement();
			if (entry.isAddition()) {
				if (wrappedSet.add(element)) {
					if (!removed.remove(element))
						added.add(element);
				}
			} else {
				if (wrappedSet.remove(element)) {
					removed.add(element);
					added.remove(element);
