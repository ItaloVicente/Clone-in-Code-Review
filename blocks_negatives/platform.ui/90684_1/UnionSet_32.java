	private ISetChangeListener<E> childSetChangeListener = new ISetChangeListener<E>() {
		@Override
		public void handleSetChange(SetChangeEvent<? extends E> event) {
			processAddsAndRemoves(event.diff.getAdditions(), event.diff.getRemovals());
		}
	};
