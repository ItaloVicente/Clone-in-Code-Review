	private ISetChangeListener<E> listener = event -> {
		for (E element : event.diff.getAdditions()) {
			addListenerTo(element);
		}
		for (E element : event.diff.getRemovals()) {
			removeListenerFrom(element);
