	private IListChangeListener<E> innerChangeListener = new IListChangeListener<E>() {
		@Override
		public void handleListChange(ListChangeEvent<? extends E> event) {
			if (!updating) {
				fireListChange(Diffs.unmodifiableDiff(event.diff));
			}
