	private ISetChangeListener<E> innerChangeListener = new ISetChangeListener<E>() {
		@Override
		public void handleSetChange(SetChangeEvent<? extends E> event) {
			if (!updating) {
				fireSetChange(Diffs.<E> unmodifiableDiff(event.diff));
			}
