		public void handleListChange(ListChangeEvent<? extends E> event) {
			Set<E> added = new HashSet<>();
			Set<E> removed = new HashSet<>();
			ListDiffEntry<? extends E>[] differences = event.diff.getDifferences();
			for (ListDiffEntry<? extends E> entry : differences) {
				E element = entry.getElement();
