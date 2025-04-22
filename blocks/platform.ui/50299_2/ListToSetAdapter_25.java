		public void handleListChange(ListChangeEvent<E> event) {
			Set<E> added = new HashSet<E>();
			Set<E> removed = new HashSet<E>();
			List<ListDiffEntry<E>> differences = event.diff
					.getDifferencesAsList();
			for (ListDiffEntry<E> entry : differences) {
				E element = entry.getElement();
