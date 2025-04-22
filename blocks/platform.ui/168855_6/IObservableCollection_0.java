	@Override
	public default Stream<E> stream() {
		ObservableTracker.getterCalled(this);
		return Collection.super.stream();
	}

	@Override
	public default Spliterator<E> spliterator() {
		ObservableTracker.getterCalled(this);
		return Collection.super.spliterator();
	}

	@Override
	public default Stream<E> parallelStream() {
		ObservableTracker.getterCalled(this);
		return Collection.super.parallelStream();
	}
